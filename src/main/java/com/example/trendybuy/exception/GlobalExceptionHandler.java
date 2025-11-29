// file: src/main/java/com/example/trendybuy/exception/GlobalExceptionHandler.java
package com.example.trendybuy.exception;

import com.example.trendybuy.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status,
                                                        String code,
                                                        String message) {
        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .status(status.value())
                        .code(code)
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // 🔹 Bütün BaseException-lar üçün (UserNotFound, InvalidOtp və s.)
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        return buildResponse(
                ex.getStatus(),
                ex.getCode().name(),
                ex.getMessage()
        );
    }

    // 🔹 Validation xətaları (@Valid, @NotBlank və s.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + " " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ExceptionCode.VALIDATION_ERROR.name(),
                msg
        );
    }

    // 🔹 Qalan bütün gözlənilməyən xətalar
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex) {

        // burda log yaza bilərsən
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ExceptionCode.INTERNAL_SERVER_ERROR.name(),
                "Internal server error"
        );
    }
}
