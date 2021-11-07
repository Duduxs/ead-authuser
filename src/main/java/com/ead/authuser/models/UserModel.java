package com.ead.authuser.models;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static com.ead.authuser.enums.UserStatus.ACTIVE;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "`user`")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(of = {"cpf"}, callSuper = false)
@JsonInclude(NON_NULL)
@Getter
public final class UserModel extends Auditable {

    @Column(nullable = false, unique = true, length = 50)
    private final String username;

    @Column(nullable = false, unique = true, length = 50)
    private final String email;

    @Column(nullable = false, unique = true, length = 14)
    private final String cpf;

    @Column(nullable = false, length = 20)
    @JsonIgnore
    @Setter
    private String password;

    @Column(nullable = false, length = 100)
    @Setter
    private String fullName;

    @Column(nullable = false)
    @Enumerated(STRING)
    @Setter
    private UserStatus status = ACTIVE;

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
