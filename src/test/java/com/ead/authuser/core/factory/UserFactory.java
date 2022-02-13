package com.ead.authuser.core.factory;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;

import java.util.Set;

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
                "https://www.freeimages.com/image/12901331",
                Set.of()
        );
    }

    public UserModel createAnotherUser() {
        return new UserModel(
                "Raphael",
                "raphaelGon@gmail.com",
                "30420180400",
                "raphael3",
                "Raphael R",
                UserStatus.ACTIVE,
                UserType.INSTRUCTOR,
                "81912348333",
                "https://www.freeimages.com/image/11123313",
                Set.of()
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
                user.getImgUrl(),
                user.getType(),
                user.getStatus()
        );
    }
}
