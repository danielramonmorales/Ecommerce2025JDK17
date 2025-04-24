package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.domain.port.IUserRepository;

public class RegistrationService {

    private final IUserRepository iUserRepository;

    public RegistrationService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public User register (User user){
        return iUserRepository.save(user);
    }
}
