package com.rawlabs.spacelabs.exception;

import lombok.Getter;

public class SpaceLabsException extends RuntimeException {

    @Getter
    private String code;

    public SpaceLabsException(String message, String code) {
        super(message);
        this.code = code;
    }

}
