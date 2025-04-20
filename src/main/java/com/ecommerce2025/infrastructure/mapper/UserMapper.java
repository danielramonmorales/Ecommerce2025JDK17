package com.ecommerce2025.infrastructure.mapper;

import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.infrastructure.dto.entity.UserEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings(
            {
                    @Mapping(source = "id", target = "id"),
                    @Mapping(source = "username", target = "username"),
                    @Mapping(source = "firstName", target = "firstName"),
                    @Mapping(source = "lastName", target = "lastName"),
                    @Mapping(source = "email" , target = "email"),
                    @Mapping(source = "address" , target = "address"),
                    @Mapping(source = "cellphone" , target = "cellphone"),
                    @Mapping(source = "password" , target = "password"),
                    @Mapping(source = "userType" , target = "userType"),
                    @Mapping(source = "dateCreated" , target = "dateCreated"),
                    @Mapping(source = "dateUpdate" , target = "dateUpdate")

            }
    )
    User toUser(UserEntity userEntity);
    Iterable<User> toUsers( Iterable<UserEntity> userEntities);

    @InheritConfiguration
    UserEntity toUserEntity(User user);


}