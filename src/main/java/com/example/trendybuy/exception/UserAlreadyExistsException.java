// file: src/main/java/com/example/trendybuy/exception/UserAlreadyExistsException.java
package com.example.trendybuy.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException(ExceptionCode code) {
        super(code , HttpStatus.CONFLICT);
    }
}
