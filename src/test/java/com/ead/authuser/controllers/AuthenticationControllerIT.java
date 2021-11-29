package com.ead.authuser.controllers;

import com.ead.authuser.core.factory.UserFactory;
import com.ead.authuser.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthenticationControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ObjectMapper mapper;

    private final UserFactory factory = new UserFactory();

    @BeforeAll
    void registerModules() {
        mapper.findAndRegisterModules();
    }

    @Test
    @DisplayName("Insert should save an entity when passing a valid dto")
    public void insertShouldSave() throws Exception {

        final var user = factory.createDTO(factory.createUser());

        mvc.perform(
                post("/auth/signup")
                        .content(mapper.writeValueAsString(user))
                        .contentType(APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value(user.username()))
                .andExpect(jsonPath("$.email").value(user.email()))
                .andExpect(jsonPath("$.fullName").value(user.fullName()))
                .andExpect(jsonPath("$.phone").value(user.phone()))
                .andExpect(jsonPath("$.cpf").value(user.cpf()))
                .andExpect(jsonPath("$.imgUrl").value(user.imgUrl()));
    }

}
