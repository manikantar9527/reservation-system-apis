package com.persistent.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelTicketDto {
	@NotBlank(message = "ContactNumber can not be null")
	private String contactNumber;
	@NotNull(message = "TicketId can not be null")
	private Long ticketId;
}
