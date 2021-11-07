package com.ead.authuser.controllers;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

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
    public ResponseEntity<Collection<UserModel>> findAll() {
        final var users = service.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping( "{id}")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
