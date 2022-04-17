package com.ead.authuser.controllers;

import com.ead.authuser.dtos.InstructorDTO;
import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.enums.RoleType;
import com.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ead.authuser.enums.ActionType.UPDATE;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

    private final UserService service;

    public InstructorController(final UserService service) {
        this.service = service;
    }

    @PatchMapping("/subscription")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUserToInstructor(@RequestBody @Valid final InstructorDTO dto) {

        log.debug("[PATCH] INIT - updateUserToInstructor() - userID {}", dto.getUserId());

        var entityUpdated = service.updateToInstructor(dto);

        log.debug("[PATCH] INFO - updateUserToInstructor() - User updated successfully");
        log.info("[PATCH] INFO - updateUserToInstructor() - User updated successfully");

        service.publishUserBy(UPDATE, entityUpdated);

        log.debug("[PATCH] FINISH - updateUserToInstructor() - USER PUBLISHED {}", entityUpdated.toString());
        log.info("[PATCH] FINISH - updateUserToInstructor() - User published successfully userID {}", entityUpdated.id());

        return ResponseEntity.ok(entityUpdated);

    }

}
