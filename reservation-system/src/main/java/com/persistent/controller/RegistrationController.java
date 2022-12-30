package com.persistent.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.dao.Availability;
import com.persistent.dto.AvailabilityDto;
import com.persistent.dto.PassengerDto;
import com.persistent.dto.StatusDto;
import com.persistent.service.AvailabilityService;
import com.persistent.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RegistrationController {

	@Autowired
	private RegistrationService service;

	@Autowired
	private AvailabilityService availabilityService;

	@PostMapping("passenger/registration")
	public ResponseEntity<StatusDto> addPassengerDetails(@Valid @RequestBody PassengerDto passenger) {
		log.info("addPassengerDetails() excecution - started");
		return ResponseEntity.ok(service.addPassengerDetails(passenger));
	}

	@PostMapping("ticket/availability")
	public ResponseEntity<List<Availability>> ticketAvailability(@Valid @RequestBody AvailabilityDto reqDto) {
		log.info("ticketAvailability() excecution - started");
		return ResponseEntity.ok(availabilityService.ticketAvailability(reqDto));
	}

}
