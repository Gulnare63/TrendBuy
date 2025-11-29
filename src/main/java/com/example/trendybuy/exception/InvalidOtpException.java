// file: src/main/java/com/example/trendybuy/exception/InvalidOtpException.java
package com.example.trendybuy.exception;

import org.springframework.http.HttpStatus;

public class InvalidOtpException extends BaseException {
    public InvalidOtpException(ExceptionCode code) {
        super(code, HttpStatus.BAD_REQUEST);
    }
}
