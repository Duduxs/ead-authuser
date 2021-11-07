package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.ead.authuser.dtos.UserDTO.UserView.RegistrationPost;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    private final UserService service;

    @Autowired
    public AuthenticationController(final UserService service) {
        this.service = service;
    }

    @PostMapping("signup")
    public ResponseEntity<UserDTO> insert(
            @RequestBody
            @Valid
            @JsonView(RegistrationPost.class)
            final UserDTO dto
    ) {
        final URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(dto.id())
            .toUri();

        final var insertedEntity = service.save(dto);

        return ResponseEntity.created(uri).body(insertedEntity);
    }
}
