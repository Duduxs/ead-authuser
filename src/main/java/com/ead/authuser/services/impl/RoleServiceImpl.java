package com.ead.authuser.services.impl;

import com.ead.authuser.enums.RoleType;
import com.ead.authuser.exceptions.InternalServerErrorHttpException;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.repositories.RoleRepository;
import com.ead.authuser.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public RoleModel findByName(RoleType name) {
        return repository.findByName(name)
                .orElseThrow(() -> new InternalServerErrorHttpException("Role not found by name: " + name.toString()));
    }
}
