package com.persistent.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Passenger {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passenger_seq")
	@SequenceGenerator(name = "passenger_seq", allocationSize = 5)
	@Column(name = "passenger_id", unique = true, nullable = false)
	private Long userId;
	private String name;
	private String password;
	@Column(name="contact_number")
	private String contactNumber;
	@Column(name="email")
	private String emailId;
	private String gender;
	private Integer age;
	private String address;
	
	/*
	 * @ManyToOne private Role role;
	 */
	
	/*
	 * @Column(name="user_type") private String userType;
	 */
	
	
	
	@CreatedDate
	private Date createdOn;
	public Passenger(Long userId) {
		super();
		this.userId = userId;
	}
	
}
