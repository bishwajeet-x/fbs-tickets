package com.flightapp.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionAdvice extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorMessage> handleGenericNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ErrorMessage("NotFoundException:: "+e.getMessage(), 404, LocalDateTime.now()),
        		HttpStatus.OK);
    } 
	
	@ExceptionHandler(IllegalInputException.class)
	public ResponseEntity<ErrorMessage> handleGenericIllegalInputException(NotFoundException e) {
        return new ResponseEntity<>(new ErrorMessage("IllegalInputException:: "+e.getMessage(), 422, LocalDateTime.now()),
        		HttpStatus.OK);
    } 
	
	@ExceptionHandler(SomethingWentWrong.class)
	public ResponseEntity<ErrorMessage> handleGenericSomethingWentWrong(SomethingWentWrong e) {
        return new ResponseEntity<>(new ErrorMessage("SomethingWentWrong:: "+e.getMessage(), 500, LocalDateTime.now()),
        		HttpStatus.OK);
    }
	
	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ErrorMessage> handleGenericException(GenericException e) {
        return new ResponseEntity<>(new ErrorMessage("GenericException:: "+e.getMessage(), 200, LocalDateTime.now()),
        		HttpStatus.OK);
    }
}
