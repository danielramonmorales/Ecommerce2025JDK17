package com.ecommerce2025.infrastructure.security.controller;


import com.ecommerce2025.application.RegistrationService;
import com.ecommerce2025.domain.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/security")
public class RegistrationController {

    private final RegistrationService registrationService;


    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> register (@RequestBody User user){
        return new ResponseEntity<>(registrationService.register(user), HttpStatus.CREATED);

    }


}
