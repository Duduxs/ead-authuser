package com.ead.authuser.services;

import com.ead.authuser.dtos.UserCourseDTO;
import com.ead.authuser.models.UserModel;

import java.util.UUID;

public interface UserCourseService {

    UserCourseDTO saveBy(final UserModel user, final UUID courseId);

    void throwIfUserIsAlreadyRegistered(final UserModel userModel, final UUID courseId);
}
