// file: src/main/java/com/example/trendybuy/exception/PasswordCannotMatchException.java
package com.example.trendybuy.exception;

import org.springframework.http.HttpStatus;

public class PasswordCannotMatchException extends BaseException {
    public PasswordCannotMatchException(ExceptionCode code) {
        super(code, HttpStatus.BAD_REQUEST);
    }
}
