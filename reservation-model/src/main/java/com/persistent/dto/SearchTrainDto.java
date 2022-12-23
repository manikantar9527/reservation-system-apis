package com.persistent.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchTrainDto {
	@NotNull(message = "Source can not be null")
	private String source;
	@NotNull(message = "Destination can not be null")
	private String destination;
	@NotNull(message = "Date can not be null")
	@Future(message = "The date must be in the future.")
	private Date date;
}
