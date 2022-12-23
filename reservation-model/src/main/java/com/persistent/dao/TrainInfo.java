package com.persistent.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "train_info")
public class TrainInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passenger_seq")
	@SequenceGenerator(name = "passenger_seq", allocationSize = 5)
	@Column(name = "train_id", unique = true, nullable = false)
	private Long trainId;
	private String trainNumber;
	private String trainName;
	private String source;
	private String destination;

	private String departureLocation;
	private String availableDays;
	private String departuretime;
	private Integer totalSeats;
	private Integer noOfCoaches;

	
	private String arrivalTime;

	@CreatedDate
	private Date createdOn;

	public TrainInfo(Long trainId) {
		super();
		this.trainId = trainId;
	}

}