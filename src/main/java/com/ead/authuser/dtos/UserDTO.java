package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record UserDTO(
        UUID id,
        @JsonView(UserView.RegistrationPost.class)
        String username,
        @JsonView(UserView.RegistrationPost.class)
        String email,
        @JsonView({ UserView.RegistrationPost.class, UserView.PasswordPut.class })
        String password,
        @JsonView(UserView.PasswordPut.class)
        String oldPassword,
        @JsonView({ UserView.RegistrationPost.class, UserView.UserPut.class })
        String fullName,
        @JsonView({ UserView.RegistrationPost.class, UserView.UserPut.class })
        String phone,
        @JsonView(UserView.RegistrationPost.class)
        String cpf,
        @JsonView(UserView.ImagePut.class)
        String imgUrl
) {
    public interface UserView {
        interface RegistrationPost {}
        interface UserPut {}
        interface PasswordPut {}
        interface ImagePut {}
    }
}