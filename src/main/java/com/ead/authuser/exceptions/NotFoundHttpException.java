package com.ead.authuser.exceptions;

public final class NotFoundHttpException extends RuntimeException {

    public NotFoundHttpException() {
        this("Entity not found!");
    }

    public NotFoundHttpException(final String msg) {
        super(msg);
    }
}
