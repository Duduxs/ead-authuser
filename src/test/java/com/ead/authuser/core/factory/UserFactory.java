package com.ead.authuser.core.factory;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;

public class UserFactory {

    public UserModel createEmptyUser() { return new UserModel(); }

    public UserModel createUser() {
        return new UserModel(
                "duduxs",
                "duduxss3@gmail.com",
                "11270099400",
                "dudu123",
                "Eduardo J",
                UserStatus.BLOCKED,
                UserType.STUDENT,
                "81912348213",
                "https://www.freeimages.com/image/12901331"
        );
    }

    public UserDTO createDTO(final UserModel user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getPassword(),
                user.getFullName(),
                user.getPhone(),
                user.getCpf(),
                user.getImgUrl()
        );
    }
}
