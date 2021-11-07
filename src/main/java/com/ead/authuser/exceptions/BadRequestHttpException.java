package com.ead.authuser.exceptions;

public final class BadRequestHttpException extends RuntimeException {

    public BadRequestHttpException() {
        this("Bad Request!");
    }

    public BadRequestHttpException(final String msg) {
        super(msg);
    }
}
