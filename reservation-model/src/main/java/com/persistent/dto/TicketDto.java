package com.persistent.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
	private Long ticketId;
	private Double ticketCost;
	private String startingLocation;
	private String destination;
	private Integer status;
	private Date date;
	private String berthType;
	private String seatNumber;
	private String coach;
	private String classType;
	private String pnr;
}
