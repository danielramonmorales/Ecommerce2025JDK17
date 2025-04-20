package com.ecommerce2025.domain.port;

import com.ecommerce2025.domain.model.User;

public interface IUserRepository {
    User save(User user);
    User findByEmail(String email);
    User findById(Integer id);
}
