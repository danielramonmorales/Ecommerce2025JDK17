package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.domain.port.IUserRepository;
import com.ecommerce2025.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Component;


@Component
public class UserJpaRepositoryImpl implements IUserRepository {
    private final IUserJpaRepository iUserJpaRepository;
    private final UserMapper userMapper;

    public UserJpaRepositoryImpl(IUserJpaRepository iUserJpaRepository, UserMapper userMapper) {
        this.iUserJpaRepository = iUserJpaRepository;
        this.userMapper = userMapper;
    }


    @Override
    public User save(User user) {
        return  userMapper.toUser(iUserJpaRepository.save(userMapper.toUserEntity(user)));
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.toUser(iUserJpaRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email: "+email+" no encontrado")
        ));
    }

    @Override
    public User findById(Integer id) {
        return userMapper.toUser(
                iUserJpaRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Usuario con id: " + id + " no existe"))
        );
    }

}
