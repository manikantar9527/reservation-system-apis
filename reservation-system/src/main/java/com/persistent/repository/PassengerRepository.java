package com.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistent.dao.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long>{


	Passenger findByUserId(Long userId);

	Passenger findByContactNumber(String contactNumber);

	Object findByUserIdOrContactNumber(Long userId,String contactNumber);

}
