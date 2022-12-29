package com.persistent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistent.dao.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long>{


	Optional<Passenger> findByUserId(Long userId);

	Optional<Passenger> findByContactNumber(String contactNumber);

	Optional<Passenger> findByUserIdOrContactNumber(Long userId,String contactNumber);

}
