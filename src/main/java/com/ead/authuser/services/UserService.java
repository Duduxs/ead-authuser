package com.ead.authuser.services;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface UserService {

    Page<UserModel> findAll(final Pageable pageable, final Specification<UserModel> spec);

    UserModel findById(final UUID id);

    void deleteById(final UUID id);

    UserDTO save(final UserDTO dto);

    UserDTO update(final UUID id, final UserDTO dto);

    void updatePassword(final UUID id, final UserDTO dto);

    UserDTO updateImage(final UUID id, final UserDTO dto);
}
