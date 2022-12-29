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
public class SearchTrainDto {
	@NotBlank(message = "Source can not be null")
	private String source;
	@NotBlank(message = "Destination can not be null")
	private String destination;
	@NotBlank(message = "Date can not be null")
	@Future(message = "The date must be in the future.")
	private Date date;
}
