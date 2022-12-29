package com.persistent.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityDto {
	@NotBlank(message = "Date can not be null")
	@Future(message = "The date must be in the future.")
	private Date Date;
	@NotBlank(message = "TrainId can not be null")
	private Long trainId;
	@NotBlank(message = "ClassType can not be null")
	private String classType;
}
