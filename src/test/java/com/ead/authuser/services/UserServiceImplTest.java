package com.ead.authuser.services;

import com.ead.authuser.mappers.UserMapper;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl service;

    @BeforeEach
    void clearMocks() {
        reset(repository, mapper);
    }

    @Nested
     class READ {

        @Test
        @DisplayName("FindById should return an entity when id exists")
        public void findByIdShouldReturn() {
            final var user = new UserModel();

            doReturn(Optional.of(user)).when(repository).findById(any());

            assertDoesNotThrow(() ->
                    assertNotNull(service.findById(UUID.randomUUID()))
            );

            verify(repository).findById(any());
        }

    }
}
