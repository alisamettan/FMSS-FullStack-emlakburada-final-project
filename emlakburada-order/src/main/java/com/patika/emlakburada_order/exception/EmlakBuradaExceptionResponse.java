package com.patika.emlakburada_order.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class EmlakBuradaExceptionResponse {

    private String message;
    private HttpStatus status;
    private LocalDateTime time;
}
