package com.persistent.dto;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
	private Long ticketId;
	private String ticketNumber;
	private String ticketName;
	private String trainId;
	private Long userId;

	private Double ticketCost;
	private String startingLocation;
	private String destination;
	@JsonIgnore
	private Integer availableSeats;

	@CreatedDate
	private Date createdOn;

}
