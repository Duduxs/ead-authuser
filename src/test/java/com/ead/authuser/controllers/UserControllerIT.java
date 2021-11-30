package com.ead.authuser.controllers;

import com.ead.authuser.core.factory.UserFactory;
import com.ead.authuser.exceptions.NotFoundHttpException;
import com.ead.authuser.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository repository;

    private final UserFactory factory = new UserFactory();

    @BeforeAll
    void registerModules() {
        mapper.findAndRegisterModules();
    }

    @Nested
    class READ {

        @Test
        @DisplayName("FindById should found an entity when passing a valid id")
        public void findByIdShouldFound() throws Exception {

            final var user = repository.save(factory.createUser());

            mvc.perform(get("/users/" + user.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(user.getId().toString()))
                    .andExpect(jsonPath("$.username").value(user.getUsername()))
                    .andExpect(jsonPath("$.email").value(user.getEmail()))
                    .andExpect(jsonPath("$.fullName").value(user.getFullName()))
                    .andExpect(jsonPath("$.status").value(user.getStatus().toString()))
                    .andExpect(jsonPath("$.type").value(user.getType().toString()))
                    .andExpect(jsonPath("$.phone").value(user.getPhone()))
                    .andExpect(jsonPath("$.cpf").value(user.getCpf()))
                    .andExpect(jsonPath("$.imgUrl").value(user.getImgUrl()));
        }

        @Test
        @DisplayName("FindById should throw an exception when passing a not valid id")
        public void findByIdShouldThrowException() throws Exception {
            mvc.perform(get("/users/" + UUID.randomUUID()))
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundHttpException))
                    .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), "Entity not found!"));
        }

        @Test
        @DisplayName("FindAll should be able to list all entities")
        public void findAllShouldReturnEntities() throws Exception {

            repository.saveAll(List.of(factory.createUser()));

            mvc.perform(get("/users"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(1));
        }
    }

    @Nested
    class UPDATE {

        @Test
        @DisplayName("Update should update entity when pass a valid id")
        @SneakyThrows
        public void updateShouldUpdateEntityWhenPassAValidId() {

            final var user = repository.save(factory.createUser());

            final var userWithNewData = factory.createDTO(factory.createAnotherUser());

            mvc.perform(
                            put("/users/" + user.getId())
                                    .content(mapper.writeValueAsString(userWithNewData))
                                    .contentType(APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(user.getId().toString()))
                    .andExpect(jsonPath("$.email").value(user.getEmail()))
                    .andExpect(jsonPath("$.cpf").value(user.getCpf()))
                    .andExpect(jsonPath("$.username").value(user.getUsername()))
                    .andExpect(jsonPath("$.fullName").value(userWithNewData.fullName()))
                    .andExpect(jsonPath("$.phone").value(userWithNewData.phone()));

        }

        @Test
        @DisplayName("Update should update password when pass a valid id")
        @SneakyThrows
        public void updateShouldUpdatePasswordWhenPassAValidId() {

            final var user = repository.save(factory.createUser());

            final var userWithNewData = factory.createDTO(user);

            mvc.perform(
                    patch("/users/" + user.getId() + "/password")
                            .content(mapper.writeValueAsString(userWithNewData))
                            .contentType(APPLICATION_JSON)
            ).andExpect(status().isAccepted());

        }

        @Test
        @DisplayName("Update should update image when pass a valid id")
        @SneakyThrows
        public void updateShouldUpdateImageWhenPassAValidId() {

            final var user = repository.save(factory.createUser());

            user.setImgUrl("https://www.freeimages.com/img/121313");
            final var userWithNewData = factory.createDTO(user);

            mvc.perform(
                            patch("/users/" + user.getId() + "/image")
                                    .content(mapper.writeValueAsString(userWithNewData))
                                    .contentType(APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andExpect(jsonPath("$.imgUrl").value(userWithNewData.imgUrl()));

        }
    }

    @Nested
    class DELETE {

        @Test
        @DisplayName("Delete should delete entity when pass a valid id")
        @SneakyThrows
        public void deleteShouldDeleteEntityWhenPassAValidId() {

            final var user = repository.save(factory.createUser());

            mvc.perform(
                    delete("/users/" + user.getId())
            ).andExpect(status().isNoContent());
        }
    }


}
