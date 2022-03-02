package com.ead.authuser.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseDTO;
import com.ead.authuser.dtos.UserCourseDTO;
import com.ead.authuser.services.UserCourseService;
import com.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    private final CourseClient client;

    private final UserService service;

    private final UserCourseService userCourseService;

    public UserCourseController(
            final CourseClient client,
            final UserService service,
            final UserCourseService userCourseService
    ) {
        this.client = client;
        this.service = service;
        this.userCourseService = userCourseService;
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

    @PostMapping("users/{userId}/courses/subscription")
    public ResponseEntity<UserCourseDTO> subscribeUserInCourse(
            @PathVariable("userId") final UUID userId,
            @Valid @RequestBody UserCourseDTO dto
    ) {

        log.debug("[GET] INIT - subscribeUserInCourse()");

        final var user = service.findById(userId);

        final var result = userCourseService.saveBy(user, dto.courseId());

        log.debug("[GET] FINISH - subscribeUserInCourse()");

        return ResponseEntity.ok(result);

    }

    @DeleteMapping("users/courses/{courseId}")
    public ResponseEntity<Void> deleteUserInCourseBy(@PathVariable final UUID courseId) {

        log.debug("[DELETE] INIT - deleteUserInCourseBy()");

        userCourseService.deleteBy(courseId);

        log.debug("[DELETE] FINISH - deleteUserInCourseBy()");

        return ResponseEntity.noContent().build();

    }
}
