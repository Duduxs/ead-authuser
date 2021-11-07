package com.ead.authuser.services;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.models.UserModel;

import java.util.Collection;
import java.util.UUID;

public interface UserService {

    Collection<UserModel> findAll();

    UserModel findById(final UUID id);

    void deleteById(final UUID id);

    UserDTO save(final UserDTO dto);
}
