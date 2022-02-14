package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserCourseDTO;
import com.ead.authuser.exceptions.ConflictHttpException;
import com.ead.authuser.mappers.UserCourseMapper;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.services.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    private final UserCourseRepository repository;

    private final UserCourseMapper userCourseMapper;

    @Autowired
    public UserCourseServiceImpl(
            final UserCourseRepository repository,
            final UserCourseMapper userCourseMapper
    ) {
        this.repository = repository;
        this.userCourseMapper = userCourseMapper;
    }

    @Transactional
    public UserCourseDTO saveBy(final UserModel user, final UUID courseId) {

        throwIfUserIsAlreadyRegistered(user, courseId);

        final var userCourse = userCourseMapper.toDomain(user, courseId);

        return userCourseMapper.toDTO(repository.save(userCourse));

    }

    @Override
    @Transactional
    public void throwIfUserIsAlreadyRegistered(final UserModel userModel, final UUID courseId) {

         if(repository.existsByUserAndCourseId(userModel, courseId)) {
             throw new ConflictHttpException("This user is already registered in course " + courseId);
         }

    }
}
