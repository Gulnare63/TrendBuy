// file: src/main/java/com/example/trendybuy/exception/AccountNotActiveException.java
package com.example.trendybuy.exception;

import org.springframework.http.HttpStatus;

public class AccountNotActiveException extends BaseException {
    public AccountNotActiveException(ExceptionCode code) {
        super(code, HttpStatus.FORBIDDEN);
    }
}
