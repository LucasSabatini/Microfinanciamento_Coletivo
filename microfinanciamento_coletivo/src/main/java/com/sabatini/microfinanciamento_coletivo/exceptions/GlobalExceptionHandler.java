package com.sabatini.microfinanciamento_coletivo.exceptions;

import com.sabatini.microfinanciamento_coletivo.service.exceptions.DataBindingViolationException;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.ObjectNotFoundException;
import com.sabatini.microfinanciamento_coletivo.service.exceptions.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
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


@RestControllerAdvice
public class GlobalExceptionHandler{

    @Value("${server.error.include-exception}")
    private boolean printStrackTrace;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handlerMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request){

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação. Cheque o campo 'errors' para mais detalhes");

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

        return buildErrorResponse(
                exception,
                errorMessage,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException,
            WebRequest request) {
        return buildErrorResponse(
                constraintViolationException,
                HttpStatus.UNPROCESSABLE_ENTITY,
                request);
    }

    @ExceptionHandler(DataBindingViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleUserJaCadastradoException(
            DataBindingViolationException dataBindingViolationException,
            WebRequest request) {
        return buildErrorResponse(
                dataBindingViolationException,
                HttpStatus.UNPROCESSABLE_ENTITY,
                request);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlerObjectNotFoundException(
            ObjectNotFoundException objectNotFoundException,
            WebRequest request){

        return buildErrorResponse(objectNotFoundException, HttpStatus.NOT_FOUND, request);
    }
  
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException userNotFoundException,
            WebRequest request) {
        return buildErrorResponse(
                userNotFoundException,
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException,
            WebRequest request) {
        String errorMessage = dataIntegrityViolationException.getMostSpecificCause().getMessage();
        return buildErrorResponse(
                dataIntegrityViolationException,
                errorMessage,

                HttpStatus.CONFLICT,
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
