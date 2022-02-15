package com.ead.authuser.controllers;

import com.ead.authuser.dtos.InstructorDTO;
import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity<UserDTO> updateUserToInstructor(@RequestBody @Valid final InstructorDTO dto) {

        log.debug("[POST] INIT - updateUserToInstructor() - userID {}", dto.getUserId());

        var entityUpdated = service.updateToInstructor(dto);

        log.debug("[POST] FINISH - updateUserToInstructor() - User updated successfully");
        log.info("[POST] FINISH - updateUserToInstructor() - User updated successfully");

        return ResponseEntity.ok(entityUpdated);

    }

}
