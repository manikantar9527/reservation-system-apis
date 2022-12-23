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
public class Availability {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_seq")
	@SequenceGenerator(name = "availability_seq", allocationSize = 5)
	@Column(name = "availability_id")
	private Long availabilityId;
	private Date date;
	@ManyToOne
	@JoinColumn(name = "train_id")
	private TrainInfo train;
	//private Integer noOfSeatsAvailable;
	
	
	
	@CreatedDate
	private Date createdOn;
	
	private Integer noOfUpperSeatsAvailable;
	private Integer noOfLowerSeatsAvailable;
	private String coach;
	
	
}