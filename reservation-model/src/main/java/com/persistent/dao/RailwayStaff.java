package com.persistent.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "railway_staff")
public class RailwayStaff {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staff_seq")
	@SequenceGenerator(name = "staff_seq", allocationSize = 5)
	@Column(name = "staff_id")
	private Long staffId;
	private String email;
	private String mobileNumber;
}
