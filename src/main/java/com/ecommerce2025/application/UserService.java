package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.domain.port.IUserRepository;

public class UserService {
    private final IUserRepository iUserRepository;

    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public User save(User user){
        return iUserRepository.save(user);
    }

    public User findById(Integer id){
        return this.iUserRepository.findById(id);
    }

    public User findByEmail(String email){
        return iUserRepository.findByEmail(email);
    }


}
