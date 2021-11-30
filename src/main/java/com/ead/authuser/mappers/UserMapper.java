package com.ead.authuser.mappers;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;

import static com.ead.authuser.enums.UserStatus.ACTIVE;
import static com.ead.authuser.enums.UserType.STUDENT;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserModel toDomain(final UserDTO userDTO);

    @Mapping(target = "password", ignore = true)
    UserDTO toDTOWithoutPassword(final UserModel userModel);

    @Mapping(target = "imgUrl", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserModel update(@MappingTarget final UserModel userModel, final UserDTO dto);

    @Mapping(target = "imgUrl", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "phone", ignore = true)
    UserModel updatePassword(@MappingTarget final UserModel userModel, final UserDTO dto);

    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserModel updateImage(@MappingTarget final UserModel userModel, final UserDTO dto);


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
