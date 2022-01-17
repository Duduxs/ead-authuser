package com.ead.authuser.services.impl;

import com.ead.authuser.mappers.UserMapper;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.services.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    private final UserCourseRepository repository;

    private final UserMapper mapper;

    @Autowired
    public UserCourseServiceImpl(final UserCourseRepository repository, final UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}
