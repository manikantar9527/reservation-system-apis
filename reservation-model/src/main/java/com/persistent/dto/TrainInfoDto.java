package com.persistent.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainInfoDto {
	private Long trainId;
	@NotNull(message = "TrainNumber can not be null")
	private String trainNumber;
	@NotNull(message = "TrainName can not be null")
	private String trainName;
	@NotNull(message = "Source can not be null")
	private String source;
	@NotNull(message = "Destination can not be null")
	private String destination;

	private String departureLocation;
	@NotNull(message = "Available Days can not be null")
	private String availableDays;
	private String departuretime;
	@NotNull(message = "TotalSeats can not be null")
	private Integer totalSeats;
	@NotNull(message = "NoOfCoaches can not be null")
	private Integer noOfCoaches;

	private String arrivalTime;

	private Date createdOn;

}