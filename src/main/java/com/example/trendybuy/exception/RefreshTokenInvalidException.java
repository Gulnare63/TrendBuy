// file: src/main/java/com/example/trendybuy/exception/RefreshTokenInvalidException.java
package com.example.trendybuy.exception;

import org.springframework.http.HttpStatus;

public class RefreshTokenInvalidException extends BaseException {

    public RefreshTokenInvalidException(ExceptionCode code) {
        super(code, HttpStatus.UNAUTHORIZED);
    }
}
