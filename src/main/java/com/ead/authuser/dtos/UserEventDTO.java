package com.ead.authuser.dtos;

import java.util.UUID;

public record UserEventDTO(
        UUID id,

        String username,

        String email,

        String fullName,

        String phone,

        String cpf,

        String imgUrl,

        String type,

        String status,

        String actionType
) {}