package com.persistent.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ReservationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final HttpStatus status;
	private final Severity severity;

	public ReservationException(String message, HttpStatus status, Severity se) {
		super(message);
		this.severity = se;
		this.status = status;
	}

}
