// file: src/main/java/com/example/trendybuy/exception/UserNotFoundException.java
package com.example.trendybuy.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(ExceptionCode code) {
        super(code, HttpStatus.NOT_FOUND);
    }
}
