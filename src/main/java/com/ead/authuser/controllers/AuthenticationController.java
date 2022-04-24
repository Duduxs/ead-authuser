package com.ead.authuser.controllers;

import com.ead.authuser.configurations.security.JwtProvider;
import com.ead.authuser.dtos.JwtDTO;
import com.ead.authuser.dtos.LoginDTO;
import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.ead.authuser.dtos.UserDTO.UserView.RegistrationPost;
import static com.ead.authuser.enums.ActionType.CREATE;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    private final UserService service;

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final JwtProvider jwtProvider;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(
            final UserService service,
            final JwtProvider jwtProvider,
            final AuthenticationManager authenticationManager
    ) {
        this.service = service;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("signup")
    public ResponseEntity<UserDTO> insert(
            @RequestBody
            @Validated(RegistrationPost.class)
            @JsonView(RegistrationPost.class)
            final UserDTO dto
    ) {

        logger.debug("[POST] INIT - insert() - DTO {}", dto.toString());

        final URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(dto.id())
            .toUri();

        final var insertedEntity = service.save(dto);

        logger.debug("[POST] INFO - insert() - DTO SAVED {}", insertedEntity.toString());
        logger.info("[POST] INFO - insert() - User saved successfully userID {}", insertedEntity.id());

        service.publishUserBy(CREATE, insertedEntity);

        logger.debug("[POST] FINISH - insert() - USER PUBLISHED {}", insertedEntity.toString());
        logger.info("[POST] FINISH - insert() - User published successfully userID {}", insertedEntity.id());

        return ResponseEntity.created(uri).body(insertedEntity);
    }

    @PostMapping("signup/admin/usr")
    public ResponseEntity<UserDTO> insertAdmin(
            @RequestBody
            @Validated(RegistrationPost.class)
            @JsonView(RegistrationPost.class)
            final UserDTO dto
    ) {

        logger.debug("[POST] INIT - insertAdmin() - DTO {}", dto.toString());

        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.id())
                .toUri();

        final var insertedEntity = service.saveAdmin(dto);

        logger.debug("[POST] INFO - insertAdmin() - DTO SAVED {}", insertedEntity.toString());
        logger.info("[POST] INFO - insertAdmin() - User saved successfully userID {}", insertedEntity.id());

        service.publishUserBy(CREATE, insertedEntity);

        logger.debug("[POST] FINISH - insertAdmin() - USER PUBLISHED {}", insertedEntity.toString());
        logger.info("[POST] FINISH - insertAdmin() - User published successfully userID {}", insertedEntity.id());

        return ResponseEntity.created(uri).body(insertedEntity);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginDTO dto) {

        final var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtProvider.generateJwt(authentication);

        return ResponseEntity.ok(new JwtDTO(jwt));

    }

}
