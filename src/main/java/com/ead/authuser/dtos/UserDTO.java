package com.ead.authuser.dtos;

import com.ead.authuser.validations.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record UserDTO(
        UUID id,

        @UsernameConstraint(
                groups = UserView.RegistrationPost.class,
                message = "must not be [empty, null, have white spaces, less than 4 and greater than 50 characters]"
        )
        @JsonView(UserView.RegistrationPost.class)
        String username,

        @NotEmpty(groups = UserView.RegistrationPost.class)
        @Length(max = 50, groups = UserView.RegistrationPost.class)
        @Email(groups = UserView.RegistrationPost.class)
        @JsonView(UserView.RegistrationPost.class)
        String email,

        @NotEmpty(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
        @Length(min = 6, max = 20, groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
        @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
        String password,

        @NotEmpty(groups = UserView.PasswordPut.class)
        @Length(min = 6, max = 20, groups = UserView.PasswordPut.class)
        @JsonView(UserView.PasswordPut.class)
        String oldPassword,

        @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
        @Length(max = 100, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
        String fullName,

        @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
        @Length(max = 20, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
        String phone,

        @NotEmpty(groups = UserView.RegistrationPost.class)
        @CPF(groups = UserView.RegistrationPost.class)
        @JsonView(UserView.RegistrationPost.class)
        String cpf,

        @NotEmpty(groups = UserView.ImagePut.class)
        @URL(groups = UserView.ImagePut.class)
        @JsonView(UserView.ImagePut.class)
        String imgUrl
) {
    public interface UserView {
        interface RegistrationPost {
        }

        interface UserPut {
        }

        interface PasswordPut {
        }

        interface ImagePut {
        }
    }

    @Override
    public String toString() {
        return String.format(
                "UserDTO[id=%s, username=%s, email=%s, fullName=%s, phone=%s, cpf=%s, imgUrl=%s]",
                id, username, email, fullName, phone, cpf, imgUrl
        );
    }
}