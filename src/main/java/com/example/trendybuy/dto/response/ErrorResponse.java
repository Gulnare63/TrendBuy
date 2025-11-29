// file: src/main/java/com/example/trendybuy/dto/ErrorResponse.java
package com.example.trendybuy.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private int status;
    private String code;      // ExceptionCode.name()
    private String message;
    private LocalDateTime timestamp;
}
