package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.dtos.UserDTO.UserView.ImagePut;
import com.ead.authuser.dtos.UserDTO.UserView.PasswordPut;
import com.ead.authuser.dtos.UserDTO.UserView.UserPut;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate.UserSpec;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import static com.ead.authuser.enums.ActionType.DELETE;
import static com.ead.authuser.enums.ActionType.UPDATE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService service;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    public UserController(final UserService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserModel> findById(@PathVariable final UUID id) {

        logger.debug("[GET] INIT - findById() - ID {}", id);

        final var user = service.findById(id);

        logger.debug("[GET] FINISH - findById() - DTO RETURNED {}", user.toString());
        logger.info("[GET] FINISH - findById() - User searched successfully userID {}", id);

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> findAll(
            @PageableDefault(sort = "createdDate", direction = DESC) final Pageable pageable,
            final UserSpec spec
    ) {

        logger.debug("[GET] INIT - findAll()");

        final var users = service.findAll(pageable, spec);

        logger.debug("[GET] FINISH - findAll()");
        logger.info("[GET] FINISH - findAll()");

        return ResponseEntity.ok(users);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable final UUID id,
            @RequestBody @Validated(UserDTO.UserView.UserPut.class) @JsonView(UserPut.class) final UserDTO dto
    ) {

        logger.debug("[PUT] INIT - update() - ID {}, DTO {}", id, dto.toString());

        var entityUpdated = service.update(id, dto);

        logger.debug("[PUT] INFO - update() - DTO SAVED {}", entityUpdated.toString());
        logger.info("[PUT] INFO - update() - User updated successfully userID {}", id);

        service.publishUserBy(UPDATE, entityUpdated);

        logger.debug("[PUT] FINISH - update() - USER PUBLISHED {}", entityUpdated.toString());
        logger.info("[PUT] FINISH - update() - User published successfully userID {}", entityUpdated.id());

        return ResponseEntity.ok(entityUpdated);
    }

    @PatchMapping( "{id}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable final UUID id,
            @RequestBody @Validated(PasswordPut.class) @JsonView(PasswordPut.class) final UserDTO dto
    ) {

        logger.debug("[PUT] INIT - updatePassword() - ID {}, DTO {}", id, dto.toString());

        service.updatePassword(id, dto);

        logger.debug("[PUT] FINISH - updatePassword() - Entity updated successfully");
        logger.info("[PUT] FINISH - updatePassword() - User updated successfully userID {}", id);

        return ResponseEntity.accepted().build();
    }

    @PatchMapping( "{id}/image")
    public ResponseEntity<UserDTO> updateImage(
            @PathVariable final UUID id,
            @RequestBody @Validated(ImagePut.class) @JsonView(ImagePut.class) final UserDTO dto
    ) {

        logger.debug("[PATCH] INIT - updateImage() - ID {}, DTO {}", id, dto.toString());

        var entityUpdated = service.updateImage(id, dto);

        logger.debug("[PATCH] INFO - updateImage() - Entity updated successfully");
        logger.info("[PATCH] INFO - updateImage() - User updated successfully userID {}", id);

        service.publishUserBy(UPDATE, entityUpdated);

        logger.debug("[PATCH] FINISH - updateImage() - USER PUBLISHED {}", entityUpdated.toString());
        logger.info("[PATCH] FINISH - updateImage() - User published successfully userID {}", entityUpdated.id());

        return ResponseEntity.ok(entityUpdated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {

        logger.debug("[DELETE] INIT - delete() - ID {}", id);

        final var entityDeletedCopy = service.delete(id);

        logger.debug("[DELETE] INFO - delete() - Entity deleted successfully");
        logger.info("[DELETE] INFO - delete() - User deleted successfully userID {}", id);

        service.publishUserBy(DELETE, entityDeletedCopy);

        logger.debug("[DELETE] FINISH - delete() - USER PUBLISHED {}", entityDeletedCopy.toString());
        logger.info("[DELETE] FINISH - delete() - User published successfully userID {}", entityDeletedCopy.id());

        return ResponseEntity.noContent().build();
    }
}
