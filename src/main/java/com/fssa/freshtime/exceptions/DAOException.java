package com.fssa.freshtime.exceptions;

import java.io.Serial;

public class DAOException extends Exception {

    @Serial
    private static final long serialVersionUID = -5868756665431641926L;

    public DAOException(String message) {
        super(message);
    }
}
