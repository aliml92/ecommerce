package com.xco.spactshop.repository;

import com.xco.spactshop.dto.TopProductRes;
import com.xco.spactshop.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.stream.Stream;


public interface ProductRepository extends MongoRepository<Product,String> {

    @Query(value="{}",
            sort="{rating: -1}",
            fields = "{ _id: 1 , name: 1, image: 1, price:1 }")
    Stream<TopProductRes> getTopProducts();

    @Query(value = "{name: {$regex: ?0, $options: 'i'}}")
    Page<Product> findAllByQ(String query, Pageable pageable);
}
