package com.persistent.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_seq")
	@SequenceGenerator(name = "ticket_seq", allocationSize = 5)
	@Column(name = "ticket_id", unique = true, nullable = false)
	private Long ticketId;
	@ManyToOne
	@JoinColumn(name = "train_id")
	private TrainInfo train;
	@ManyToOne
	@JoinColumn(name = "passenger_id")
	private Passenger passenger;

	private Double ticketCost;
	private String startingLocation;
	private String destination;
	private int status;
	private Date date;
	private String berthType;
	private String seatNumber;
	private String coach;

	@CreatedDate
	private Date createdOn;

	public Ticket(Long ticketId) {
		super();
		this.ticketId = ticketId;
	}
	
	

}
