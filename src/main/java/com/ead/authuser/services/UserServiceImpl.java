package com.ead.authuser.services;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.exceptions.NotFoundHttpException;
import com.ead.authuser.mappers.UserMapper;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(final UserRepository repository, final UserMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserModel findById(final UUID id) {
        return repository.findById(id).orElseThrow(NotFoundHttpException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<UserModel> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public UserDTO save(final UserDTO dto) {
        final var domain = mapper.toDomain(dto);
        repository.save(domain);
        return mapper.toDTOWithoutPassword(domain);
    }

    @Override
    @Transactional
    public void deleteById(final UUID id) {
        try {
            repository.deleteById(id);
        } catch (final EmptyResultDataAccessException exception) {
            throw new NotFoundHttpException("This resource can't be founded to delete.");
        }
    }




}
