package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record UserDTO(
        UUID id,
        String username,
        String email,
        String password,
        String oldPassword,
        String fullName,
        String phone,
        String cpf,
        String imgUrl
) {}