package com.ecommerce2025.domain.model;


import java.time.LocalDateTime;

public class User {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String cellphone;
    private String password;
    private UserType userType;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdate;
}
