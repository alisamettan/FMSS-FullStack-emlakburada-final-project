package com.patika.emlakburada_order.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class EmlakBuradaException extends RuntimeException{

    private HttpStatus status;

    public EmlakBuradaException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
