package com.ead.authuser.controllers;

import com.ead.authuser.clients.UserClient;
import com.ead.authuser.dtos.CourseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    private final UserClient client;

    public UserCourseController(final UserClient client) {
        this.client = client;
    }

    @GetMapping("users/{userId}/courses")
    public ResponseEntity<Page<CourseDTO>> findAllBy(
            @PathVariable("userId") final UUID userId,
            @PageableDefault(sort = "id", direction = ASC) final Pageable pageable
    ) {
        log.debug("[GET] INIT - findAllBy()");

        final var result = client.findAllBy(userId, pageable);

        log.debug("[GET] FINISH - findAllBy()");

        return ResponseEntity.ok(result);
    }
}
