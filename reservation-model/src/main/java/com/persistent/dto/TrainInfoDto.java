package com.persistent.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainInfoDto {
	private Long trainId;
	@NotBlank(message = "TrainNumber can not be null")
	private String trainNumber;
	@NotBlank(message = "TrainName can not be null")
	private String trainName;
	@NotBlank(message = "Source can not be null")
	private String source;
	@NotBlank(message = "Destination can not be null")
	private String destination;

	private String departureLocation;
	@NotBlank(message = "Available Days can not be null")
	private String availableDays;
	private String departuretime;
	@NotBlank(message = "TotalSeats can not be null")
	private Integer totalSeats;
	@NotBlank(message = "NoOfCoaches can not be null")
	private Integer noOfCoaches;

	private String arrivalTime;

	private Date createdOn;

}