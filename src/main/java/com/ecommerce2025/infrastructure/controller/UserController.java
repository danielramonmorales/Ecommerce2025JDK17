package com.ecommerce2025.infrastructure.controller;

import com.ecommerce2025.application.UserService;
import com.ecommerce2025.domain.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
//http://localhost:8080/v1/users
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    //POST = http://localhost:8080/api/v1/users
    @PostMapping
    public User save(@RequestBody User user){
        return userService.save(user);
    }
    //GET = http://localhost:8080/api/v1/users/7
    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id){
        return userService.findById(id);

    }
}
