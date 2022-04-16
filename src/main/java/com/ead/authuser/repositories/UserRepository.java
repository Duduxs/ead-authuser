package com.ead.authuser.repositories;

import com.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    @EntityGraph(attributePaths = "roles", type = FETCH)
    Optional<UserModel> findByUsername(String username);

}
