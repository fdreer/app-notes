package com.franco.appnotes.error;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<ExceptionResponse> handleEntityExistsException(
            EntityExistsException e, WebRequest request)
    {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleEntityNotFoundException(
            EntityNotFoundException e, WebRequest request)
    {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ExceptionResponse> handleBadCredentialsException(
            BadCredentialsException e, WebRequest request)
    {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .message(e.getMessage())
                .build(), HttpStatus.FORBIDDEN);
    }

//    TODO
//    @ExceptionHandler(ConstraintViolationException.class)
//    protected ResponseEntity<ExceptionResponse> handleConstraintViolationException(
//            ConstraintViolationException e, WebRequest request)
//    {
//        return new ResponseEntity<>(ExceptionResponse.builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.FORBIDDEN.value())
//                .message(e.getConstraintViolations().stream().iterator().toString())
//                .build(), HttpStatus.FORBIDDEN);
//    }
}
