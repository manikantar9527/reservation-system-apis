package com.persistent.exception;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllExceptionMethod(Exception ex, WebRequest requset) {

		ExceptionMessage exceptionMessageObj = new ExceptionMessage();

		// Handle All Field Validation Errors
		if (ex instanceof MethodArgumentNotValidException) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage());
				sb.append(";");
			}
			exceptionMessageObj.setMessage(sb.toString());
		} else {
			exceptionMessageObj.setMessage(ex.getLocalizedMessage());
		}

		exceptionMessageObj.setError(ex.getClass().getCanonicalName());
		exceptionMessageObj.setPath(((ServletWebRequest) requset).getRequest().getServletPath());

		// return exceptionMessageObj;
		return new ResponseEntity<>(exceptionMessageObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ReservationException.class)
	public ResponseEntity<ErrorDto> handleReservationException(ReservationException e, WebRequest request) {
		ErrorDto errorMessage = ErrorDto.builder().id(UUID.randomUUID()).severity(e.getSeverity())
				.message(e.getMessage()).currentTime(OffsetDateTime.now()).build();
		return ResponseEntity.status(e.getStatus()).body(errorMessage);
	}
}
