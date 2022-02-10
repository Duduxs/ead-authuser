package com.ead.authuser.dtos;

import com.ead.authuser.enums.CourseLevel;
import com.ead.authuser.enums.CourseStatus;

import java.util.UUID;

public record CourseDTO(

        UUID id,

        String name,

        String description,

        String imgUrl,

        CourseStatus status,

        UUID instructorId,

        CourseLevel level
) {}
