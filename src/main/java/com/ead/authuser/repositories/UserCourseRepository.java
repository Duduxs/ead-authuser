package com.ead.authuser.repositories;

import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {

    boolean existsByUserAndCourseId(final UserModel userModel, final UUID courseId);

    @Query(value = "select * from tb_users_courses where user_id = :userId", nativeQuery = true)
    Collection<UserCourseModel> findAllBy(final UUID userId);

    boolean existsByCourseId(final UUID courseId);

    void deleteAllByCourseId(final UUID courseId);
}
