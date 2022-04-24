package com.ead.authuser.mappers;

import com.ead.authuser.annotations.EncodedMapping;
import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.dtos.UserEventDTO;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;

import java.util.Set;

import static com.ead.authuser.enums.UserStatus.ACTIVE;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "roles", ignore = true)
    UserModel toDomain(final UserDTO userDTO, final RoleModel role, final UserType type);

    @Mapping(target = "password", ignore = true)
    UserDTO toDTOWithoutPassword(final UserModel userModel);

    UserEventDTO toUserEventDTO(final UserDTO dto, final ActionType actionType);

    @Mapping(target = "imgUrl", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "status", ignore = true)
    UserModel update(@MappingTarget final UserModel userModel, final UserDTO dto);

    @Mapping(target = "imgUrl", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "status", ignore = true)
    UserModel updatePassword(@MappingTarget final UserModel userModel, final UserDTO dto);

    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "status", ignore = true)
    UserModel updateImage(@MappingTarget final UserModel userModel, final UserDTO dto);

    @ObjectFactory
    default UserModel create(final UserDTO dto, final RoleModel role, final UserType type) {
        return new UserModel(
                dto.username(),
                dto.email(),
                dto.cpf(),
                dto.password(),
                dto.fullName(),
                ACTIVE,
                type,
                dto.phone(),
                dto.imgUrl(),
                Set.of(role)
        );
    }
}
