package com.ead.authuser.entities;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "`user`")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(of = {"cpf"}, callSuper = false)
public final class User extends Auditable {

    @Column(nullable = false, unique = true, length = 50)
    private final String username;

    @Column(nullable = false, unique = true, length = 50)
    private final String email;

    @Column(nullable = false, unique = true, length = 14)
    private final String cpf;

    @Column(nullable = false, length = 90)
    @JsonIgnore
    @Setter
    private String password;

    @Column(nullable = false, length = 100)
    @Setter
    private String fullName;

    @Column(nullable = false)
    @Enumerated(STRING)
    @Setter
    private UserStatus status;

    @Column(nullable = false)
    @Enumerated(STRING)
    @Setter
    private UserType type;

    @Column(length = 20)
    @Setter
    private String phone;

    @Setter
    private String imgUrl;
}
