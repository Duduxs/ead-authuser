package com.ead.authuser.services;

import com.ead.authuser.core.factory.UserFactory;
import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.exceptions.BadRequestHttpException;
import com.ead.authuser.exceptions.NotFoundHttpException;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
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
    
    private final UserFactory factory = new UserFactory();

    @BeforeEach
    void clearMocks() {
        reset(repository, mapper);
    }

    @Nested
    class READ {

        @Test
        @DisplayName("FindById should return an entity when id exists")
        public void findByIdShouldReturn() {
            final var user = factory.createEmptyUser();

            doReturn(Optional.of(user)).when(repository).findById(any());

            assertDoesNotThrow(() ->
                    assertNotNull(service.findById(UUID.randomUUID()))
            );

            verify(repository).findById(any());
        }

        @Test
        @DisplayName("FindById should throw an exception when id not exists")
        public void findByIdShouldThrow() {

            doThrow(NotFoundHttpException.class).when(repository).findById(any());

            assertThrows(NotFoundHttpException.class, () -> service.findById(UUID.randomUUID()));

            verify(repository).findById(any());
        }

        @Test
        @DisplayName("FindAll should return an list of entities")
        public void findAll() {
            final var users = new PageImpl<>(List.of(new UserModel(), new UserModel()));

            doReturn(users).when(repository).findAll((Specification<UserModel>) any(), (Pageable) any());

            final var entities = service.findAll(any(), any());

            assertNotNull(entities);

            entities.forEach(e -> assertTrue(e.hasLinks()));

            verify(repository).findAll((Specification<UserModel>) any(), (Pageable) any());
        }
    }

    @Nested
    class INSERT {

        @Test
        @DisplayName("Save should insert entity when passing a dto")
        public void save() {
            final var user = factory.createEmptyUser();
            final var dto = factory.createDTO(user);

            doReturn(user).when(mapper).toDomain(any());
            doReturn(user).when(repository).save(any());
            doReturn(dto).when(mapper).toDTOWithoutPassword(any());

            assertNotNull(service.save(dto));

            verify(mapper).toDomain(any());
            verify(repository).save(any());
            verify(mapper).toDTOWithoutPassword(any());
        }
    }

    @Nested
    class UPDATE {

        @Test
        @DisplayName("Update should update entity when passing a id and dto")
        public void updateShouldUpdate() {
            final var user = factory.createEmptyUser();
            final var dto = factory.createDTO(user);

            doReturn(Optional.of(user)).when(repository).findById(any());
            doReturn(user).when(repository).save(any());
            doReturn(user).when(mapper).update(any(), any());
            doReturn(dto).when(mapper).toDTOWithoutPassword(any());

            assertDoesNotThrow(() -> assertNotNull(service.update(UUID.randomUUID(), dto)));

            verify(repository).findById(any());
            verify(repository).save(any());
            verify(mapper).update(any(), any());
            verify(mapper).toDTOWithoutPassword(any());
        }

        @Test
        @DisplayName("Update should throw an exception when passing a id that doesn't exists")
        public void updateShouldThrowException() {
            final var user = factory.createEmptyUser();
            final var dto = factory.createDTO(user);

            doThrow(NotFoundHttpException.class).when(repository).findById(any());

            assertThrows(NotFoundHttpException.class, () -> service.updateImage(UUID.randomUUID(), dto));

            verify(repository).findById(any());
            verify(repository, never()).save(any());
            verify(mapper, never()).update(any(), any());
            verify(mapper, never()).toDTOWithoutPassword(any());
        }

        @Test
        @DisplayName("UpdatePassword should update password of the entity when passing a id and dto")
        public void updatePasswordShouldUpdate() {
            final var user = factory.createUser();
            final var dto = factory.createDTO(user);

            doReturn(Optional.of(user)).when(repository).findById(any());
            doReturn(user).when(repository).save(any());
            doReturn(user).when(mapper).updatePassword(any(), any());

            assertDoesNotThrow(() -> service.updatePassword(UUID.randomUUID(), dto));

            verify(repository).findById(any());
            verify(repository).save(any());
            verify(mapper).updatePassword(any(), any());
        }

        @Test
        @DisplayName("UpdatePassword should throw an exception when passing dto and entity password aren't the same")
        public void updatePasswordShouldThrowException() {
            final var user = factory.createUser();
            final var dto = createDTOWithDifferentPassword(user);

            doReturn(Optional.of(user)).when(repository).findById(any());

            assertThrows(BadRequestHttpException.class, () -> service.updatePassword(UUID.randomUUID(), dto));

            verify(repository).findById(any());
            verify(repository, never()).save(any());
            verify(mapper, never()).updatePassword(any(), any());
        }

        @Test
        @DisplayName("UpdatePassword should throw a not found exception when passing dto a id that doesn't exists")
        public void updatePasswordShouldThrowNotFoundException() {
            final var user = factory.createUser();
            final var dto = createDTOWithDifferentPassword(user);

            doThrow(NotFoundHttpException.class).when(repository).findById(any());

            assertThrows(NotFoundHttpException.class, () -> service.updatePassword(UUID.randomUUID(), dto));

            verify(repository).findById(any());
            verify(repository, never()).save(any());
            verify(mapper, never()).updatePassword(any(), any());
        }

        @Test
        @DisplayName("UpdateImage should update image of the entity when passing a id and dto")
        public void updateImageShouldUpdate() {
            final var user = factory.createEmptyUser();
            final var dto = factory.createDTO(user);

            doReturn(Optional.of(user)).when(repository).findById(any());
            doReturn(user).when(repository).save(any());
            doReturn(user).when(mapper).updateImage(any(), any());
            doReturn(dto).when(mapper).toDTOWithoutPassword(any());

            assertDoesNotThrow(() -> service.updateImage(UUID.randomUUID(), dto));

            verify(repository).findById(any());
            verify(repository).save(any());
            verify(mapper).updateImage(any(), any());
            verify(mapper).toDTOWithoutPassword(any());
        }

        @Test
        @DisplayName("UpdateImage should throw an exception when passing a id that doesn't exists")
        public void updateImageShouldThrowException() {
            final var user = factory.createEmptyUser();
            final var dto = factory.createDTO(user);

            doThrow(NotFoundHttpException.class).when(repository).findById(any());

            assertThrows(NotFoundHttpException.class, () -> service.updateImage(UUID.randomUUID(), dto));

            verify(repository).findById(any());
            verify(repository, never()).save(any());
            verify(mapper, never()).updateImage(any(), any());
            verify(mapper, never()).toDTOWithoutPassword(any());
        }
    }

    @Nested
    class DELETE {

        @Test
        @DisplayName("DeleteById should remove entity when passing a valid id")
        public void deleteByIdShouldRemove() {
            doNothing().when(repository).deleteById(any());

            assertDoesNotThrow(() -> service.deleteById(UUID.randomUUID()));

            verify(repository).deleteById(any());
        }

        @Test
        @DisplayName("DeleteById should throw an exception when passing an invalid id")
        public void deleteByIdShouldThrow() {
            doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(any());

            assertThrows(NotFoundHttpException.class, () -> service.deleteById(UUID.randomUUID()));

            verify(repository).deleteById(any());
        }

    }
    
    private UserDTO createDTOWithDifferentPassword(final UserModel user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                "anotherOldPassword",
                user.getFullName(),
                user.getPhone(),
                user.getCpf(),
                user.getImgUrl()
        );
    }
}
