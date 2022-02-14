package com.ead.authuser.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(
        name = "user_course",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "courseId"})
)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
public final class UserCourseModel {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false)
    private final UUID id;

    @ManyToOne(fetch = LAZY, optional = false)
    private final UserModel user;

    @Column(nullable = false)
    private final UUID courseId;
}
