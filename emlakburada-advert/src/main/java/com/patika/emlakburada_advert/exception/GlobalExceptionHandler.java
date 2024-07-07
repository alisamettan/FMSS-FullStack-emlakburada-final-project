package com.patika.emlakburada_advert.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<EmlakBuradaExceptionResponse> handleException(EmlakBuradaException exception){
        EmlakBuradaExceptionResponse exceptionResponse=new EmlakBuradaExceptionResponse(exception.getMessage(),exception.getStatus(), LocalDateTime.now());

        return new ResponseEntity<>(exceptionResponse,exception.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<EmlakBuradaExceptionResponse> handleException(Exception exception){
        EmlakBuradaExceptionResponse exceptionResponse=new EmlakBuradaExceptionResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());

        return new ResponseEntity<>(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
