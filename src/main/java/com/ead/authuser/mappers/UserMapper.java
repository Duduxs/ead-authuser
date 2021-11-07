package com.ead.authuser.mappers;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import static com.ead.authuser.enums.UserStatus.ACTIVE;
import static com.ead.authuser.enums.UserType.STUDENT;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserModel toDomain(final UserDTO userDTO);

    UserDTO toDTO(final UserModel userModel);

    @Mapping(target = "password", ignore = true)
    UserDTO toDTOWithoutPassword(final UserModel userModel);

    @ObjectFactory
    default UserModel create(final UserDTO dto) {
        return new UserModel(
                dto.username(),
                dto.email(),
                dto.cpf(),
                dto.password(),
                dto.fullName(),
                ACTIVE,
                STUDENT,
                dto.phone(),
                dto.imgUrl()
        );
    }
}
