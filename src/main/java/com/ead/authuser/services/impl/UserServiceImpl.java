package com.ead.authuser.services.impl;

import com.ead.authuser.controllers.UserController;
import com.ead.authuser.dtos.InstructorDTO;
import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.exceptions.BadRequestHttpException;
import com.ead.authuser.exceptions.NotFoundHttpException;
import com.ead.authuser.mappers.UserMapper;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.ead.authuser.enums.UserType.INSTRUCTOR;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    public UserServiceImpl(final UserRepository repository, final UserMapper mapper) {
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
    public Page<UserModel> findAll(
            final Pageable pageable,
            final Specification<UserModel> spec,
            final UUID courseId
    ) {

        Page<UserModel> users;

        if (courseId != null) {
            users = repository.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
        } else {
            users = repository.findAll(spec, pageable);
        }

        users.forEach(u -> u.add(linkTo(methodOn(UserController.class).findById(u.getId())).withSelfRel()));

        return users;
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

        final var domain = findById(id);

        final var domainUpdated = repository.save(mapper.update(domain, dto));

        return mapper.toDTOWithoutPassword(domainUpdated);

    }

    @Override
    @Transactional
    public UserDTO updateToInstructor(final InstructorDTO dto) {

        final var domain = findById(dto.getUserId());
        domain.setType(INSTRUCTOR);

        final var domainUpdated = repository.save(domain);

        return mapper.toDTOWithoutPassword(domainUpdated);

    }

    @Override
    @Transactional
    public void updatePassword(final UUID id, final UserDTO dto) {

        final var domain = findById(id);

        if (!domain.getPassword().equals(dto.oldPassword())) {
            throw new BadRequestHttpException("Passwords do not match!");
        }

        repository.save(mapper.updatePassword(domain, dto));

    }

    @Override
    @Transactional
    public UserDTO updateImage(final UUID id, final UserDTO dto) {

        final var domain = findById(id);

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
