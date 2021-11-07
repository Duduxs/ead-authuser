package com.ead.authuser.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PACKAGE;


@NoArgsConstructor(force = true, access = PACKAGE)
@MappedSuperclass
@Getter
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = AUTO)
    private final UUID id;
}
