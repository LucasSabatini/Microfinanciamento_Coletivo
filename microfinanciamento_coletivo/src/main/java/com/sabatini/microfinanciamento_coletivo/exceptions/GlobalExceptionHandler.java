package com.sabatini.microfinanciamento_coletivo.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${server.error.include-exception}")
    private boolean printStrackTrace;



    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handlerMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request){

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validacão. Cheque o campo 'errors' para mais detalhes");

        for(FieldError fieldError: methodArgumentNotValidException.getBindingResult().getFieldErrors()){
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handlerAllUncaughtException(
            Exception exception,
            WebRequest request){

        final String errorMessage = "Ocorreu um erro desconhecido.";

        return buildErrorResponse(exception, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handlerDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException,
            WebRequest request){

        String errorMessage = dataIntegrityViolationException.getMostSpecificCause().getMessage();

        return buildErrorResponse(dataIntegrityViolationException,
                errorMessage,
                HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handlerConstrainViolationException(
            ConstraintViolationException constraintViolationException,
            WebRequest request){

        return buildErrorResponse(constraintViolationException,
                HttpStatus.UNPROCESSABLE_ENTITY,
                request);

    }

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            HttpStatus httpStatus,
            WebRequest request){

        return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
    }

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus,
            WebRequest request){

        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);

        if(this.printStrackTrace){
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
