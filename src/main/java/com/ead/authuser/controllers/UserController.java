package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.dtos.UserDTO.UserView.ImagePut;
import com.ead.authuser.dtos.UserDTO.UserView.PasswordPut;
import com.ead.authuser.dtos.UserDTO.UserView.UserPut;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.ead.authuser.specifications.SpecificationTemplate.UserSpec;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(final UserService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserModel> findById(@PathVariable final UUID id) {
        final var user = service.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> findAll(
            @PageableDefault(sort = "createdDate", direction = DESC) final Pageable pageable,
            final UserSpec spec
    ) {
        final var users = service.findAll(pageable, spec);
        return ResponseEntity.ok(users);
    }

    @PutMapping( "{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable final UUID id,
            @RequestBody @Validated(UserDTO.UserView.UserPut.class) @JsonView(UserPut.class) final UserDTO dto
    ) {
        var entityUpdated = service.update(id, dto);
        return ResponseEntity.ok(entityUpdated);
    }

    @PatchMapping( "{id}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable final UUID id,
            @RequestBody @Validated(PasswordPut.class) @JsonView(PasswordPut.class) final UserDTO dto
    ) {
        service.updatePassword(id, dto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping( "{id}/image")
    public ResponseEntity<UserDTO> updateImage(
            @PathVariable final UUID id,
            @RequestBody @Validated(ImagePut.class) @JsonView(ImagePut.class) final UserDTO dto
    ) {
        var entityUpdated = service.updateImage(id, dto);
        return ResponseEntity.ok(entityUpdated);
    }

    @DeleteMapping( "{id}")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
