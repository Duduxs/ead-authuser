package com.ead.authuser.exceptions;

public final class ConflictHttpException extends RuntimeException {

    public ConflictHttpException() {
        this("Entity conflicted!");
    }

    public ConflictHttpException(final String msg) {
        super(msg);
    }
}
