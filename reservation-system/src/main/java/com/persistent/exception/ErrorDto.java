package com.persistent.exception;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorDto {
	private UUID id;
	private Severity severity;
	private String message;
	private OffsetDateTime currentTime;
}
