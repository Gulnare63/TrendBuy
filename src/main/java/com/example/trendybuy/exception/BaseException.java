// file: src/main/java/com/example/trendybuy/exception/BaseException.java
package com.example.trendybuy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {

    private final ExceptionCode code;
    private final HttpStatus status;

    public BaseException(ExceptionCode code, HttpStatus status) {
        super(code.name()); // mesaj kimi enum adı gedir
        this.code = code;
        this.status = status;
    }
}
