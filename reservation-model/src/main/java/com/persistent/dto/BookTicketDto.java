package com.persistent.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTicketDto {
	@NotNull(message = "UserId can not be null")
	private Long userId;

	@NotNull(message = "TrainId can not be null")
	private Long trainId;

	@NotBlank(message = "ClassType can not be null")
	private String ClassType;

	private Double ticketCost;
	private String startingLocation;
	private String destination;
	@NotNull(message = "Date can not be null")
	@Future(message = "The date must be in the future.")
	private Date date;
	private String berthType;
}
