package com.example.trendybuy.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    public NotFoundException(ExceptionCode code) {
        super(code, HttpStatus.NOT_FOUND);
    }
}
