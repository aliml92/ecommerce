package com.xco.spactshop.controller;

import com.xco.spactshop.dto.CommentReq;
import com.xco.spactshop.dto.MessageRes;
import com.xco.spactshop.dto.TopProductRes;
import com.xco.spactshop.model.Product;
import com.xco.spactshop.model.User;
import com.xco.spactshop.repository.ProductRepository;
import com.xco.spactshop.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/products")
public class ProductController {



    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public ProductController(ProductRepository productRepo, UserRepository userRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTopProducts(){

        List<TopProductRes> products;
        try(Stream<TopProductRes> stream = productRepo.getTopProducts()) {
            products = stream.limit(3).collect(Collectors.toList());
        }
        System.out.println(products);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(@RequestParam(value = "keyword") String keyword,
                                           @RequestParam(value = "pageNumber") int pageNumber){
        String key = keyword.strip();
        Pageable pageable = PageRequest.of(pageNumber - 1, 8);
        if (key.length() == 0){
            Page<Product> products = productRepo.findAll(pageable);
            List<Product> p = products.getContent();
            int page = products.getNumber() + 1;
            int pages = products.getTotalPages();
            Map<String, Object> result = Map.of("page", page, "pages", pages, "products", p);
            return ResponseEntity.ok(result);
        } else {
            Page<Product> products = productRepo.findAllByQ(key, pageable);
            List<Product> p = products.getContent();
            int page = products.getNumber() + 1;
            int pages = products.getTotalPages();
            Map<String, Object> result = Map.of("page", page, "pages", pages, "products", p);
            return ResponseEntity.ok(result);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Product p = productRepo.findById(id).get();
        return ResponseEntity.ok(p);
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<?> addComment(Authentication auth, @PathVariable String id, @RequestBody CommentReq req){
        User user = userRepo.findByEmail(auth.getName()).get();
        Optional<Product> p = productRepo.findById(id);
        if (p.isPresent()){
            Product product = p.get();
            var alreadyReviewed = product.getReviews().stream().filter(x -> x.getUser().equals(user.get_id())).count();
            if (alreadyReviewed != 0){
                return ResponseEntity.status(400).body(new MessageRes("Product already reviewed"));
            }
            Product.Review r = new Product.Review();
            r.setRating(req.getRating());
            r.setComment(req.getComment());
            r.setName(user.getName());
            r.setUser(user.get_id());
            product.getReviews().add(r);
            int numReviews = product.getNumReviews() + 1;
            Double rating = (product.getRating() + req.getRating())/numReviews;
            product.setNumReviews(numReviews);
            DecimalFormat df = new DecimalFormat("#.0");
            product.setRating(Double.valueOf(df.format(rating)));
            productRepo.save(product);
            return ResponseEntity.status(201).body(new MessageRes("Review added"));

        }
        return ResponseEntity.ok(p);
    }
}
