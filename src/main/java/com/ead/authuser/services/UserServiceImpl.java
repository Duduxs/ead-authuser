package com.ead.authuser.services;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.exceptions.BadRequestHttpException;
import com.ead.authuser.exceptions.NotFoundHttpException;
import com.ead.authuser.mappers.UserMapper;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

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
    public Page<UserModel> findAll(@PageableDefault(sort = "createdDate", direction = DESC) final Pageable pageable) {
        return repository.findAll(pageable);
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
    public UserDTO update(final UUID id, final UserDTO dto) {
        var domain = findById(id);
        repository.save(mapper.update(domain, dto));
        return mapper.toDTOWithoutPassword(domain);
    }

    @Override
    @Transactional
    public void updatePassword(final UUID id, final UserDTO dto) {
        var domain = findById(id);

        if(!domain.getPassword().equals(dto.oldPassword())) {
            throw new BadRequestHttpException("Passwords do not match!");
        }

        repository.save(mapper.updatePassword(domain, dto));
    }

    @Override
    @Transactional
    public UserDTO updateImage(final UUID id, final UserDTO dto) {
        var domain = findById(id);

        repository.save(mapper.updateImage(domain, dto));
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
