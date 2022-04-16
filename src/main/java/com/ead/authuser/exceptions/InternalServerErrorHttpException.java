package com.ead.authuser.exceptions;

public final class InternalServerErrorHttpException extends RuntimeException {

    public InternalServerErrorHttpException() {
        this("Internal error occurred!");
    }

    public InternalServerErrorHttpException(final String msg) {
        super(msg);
    }
}
