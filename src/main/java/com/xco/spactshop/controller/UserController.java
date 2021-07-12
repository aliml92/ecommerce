package com.xco.spactshop.controller;

import com.xco.spactshop.dto.*;
import com.xco.spactshop.model.User;
import com.xco.spactshop.repository.UserRepository;
import com.xco.spactshop.security.MyUserDetails;
import com.xco.spactshop.security.jwt.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    public UserController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder encoder,
                          JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }



    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        List<User> users =  userRepository.findAll();
        if (users.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        boolean exists = userRepository.existsById(id);
        if (!exists){
            return ResponseEntity.notFound().build();
        } else {
            userRepository.deleteById(id);
            return ResponseEntity.ok(new MessageRes("User removed"));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id){
        return Optional.of(userRepository.findById(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication auth){
        return Optional.of(userRepository.findByEmail(auth.getName()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(Authentication auth,
                                           @RequestBody ProfileReq req,
                                            HttpServletRequest servletReq){
        Optional<User> user = userRepository.findByEmail(auth.getName());
        if (user.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            User user1 = user.get();
            if (req.getEmail() != null && req.getEmail().length() > 0){
                user1.setEmail(req.getEmail());
            }
            if (req.getName() != null && req.getName().length() > 0){
                user1.setName(req.getName());
            }
            if (req.getPassword() != null && req.getPassword().length() > 0){
                user1.setPassword(encoder.encode(req.getPassword()));
            }
            User user2 =  userRepository.save(user1);
            String token = jwtProvider.generateJwtTokenFromEmail(user2.getEmail());
           return ResponseEntity.ok(new JwtRes(user2.get_id(), user2.getName(), user2.getEmail(), token, user2.getIsAdmin()));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UpdateReq updateReq){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            User user1 = user.get();
            user1.set_id(updateReq.get_id());
            user1.setName(updateReq.getName());
            user1.setEmail(updateReq.getEmail());
            user1.setIsAdmin(updateReq.getIsAdmin());
            User user2 =  userRepository.save(user1);
            return ResponseEntity.ok(user2);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq loginReq) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateJwtToken(authentication);
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(
                new JwtRes(userDetails.getId(),
                                userDetails.getName(),
                                userDetails.getUsername(),
                                token,
                                userDetails.getIsAdmin()));
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody SignupReq signupReq) {
        if (userRepository.existsByEmail(signupReq.getEmail())) {
            return new ResponseEntity<>(new MessageRes("User already exists!"),
                    HttpStatus.BAD_REQUEST);
        }
        // Creating user's account
        User user = new User(signupReq.getName(), signupReq.getEmail(),encoder.encode(signupReq.getPassword()));
        userRepository.insert(user);
        return login(new LoginReq(signupReq.getEmail(), signupReq.getPassword()));
    }



}
