package com.persistent.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import com.persistent.dao.Passenger;
import com.persistent.dao.TrainInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTicketDto {
	@NotBlank(message = "UserId can not be null")
	private Long userId;
	private TrainInfo train;
	private Passenger passenger;
	@NotBlank(message = "TrainId can not be null")
	private Long trainId;
	
	@NotBlank(message = "ClassType can not be null")
	private String ClassType;

	private Double ticketCost;
	private String startingLocation;
	private String destination;
	@NotBlank(message = "Date can not be null")
	@Future(message = "The date must be in the future.")
	private Date date;
	private String berthType;
}
