package com.ead.authuser.mappers;

import com.ead.authuser.dtos.UserCourseDTO;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserCourseMapper {

    UserCourseModel toDomain(final UserModel user, final UUID courseId);

    UserCourseDTO toDTO(final UserCourseModel userCourseModel);

    @ObjectFactory
    default UserCourseModel create(final UserModel user, final UUID courseId) {
        return new UserCourseModel(null, user, courseId);
    }
}
