package com.ead.authuser.dtos;

import com.ead.authuser.enums.RoleType;

import java.util.UUID;

public record RoleDTO(
        UUID id,
        RoleType name
) {
}
