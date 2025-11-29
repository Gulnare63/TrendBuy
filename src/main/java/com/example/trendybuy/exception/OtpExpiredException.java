// file: src/main/java/com/example/trendybuy/exception/OtpExpiredException.java
package com.example.trendybuy.exception;

import org.springframework.http.HttpStatus;

public class OtpExpiredException extends BaseException {
    public OtpExpiredException(ExceptionCode code) {
        super(code , HttpStatus.BAD_REQUEST);
    }
}
