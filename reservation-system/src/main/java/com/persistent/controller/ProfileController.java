package com.persistent.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.dto.PassengerDto;
import com.persistent.dto.StatusDto;
import com.persistent.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("rest")
public class ProfileController {

	@Autowired
	private RegistrationService service;

	@PostMapping("update/profile")
	public ResponseEntity<StatusDto> updatePassengerDetails(@Valid @RequestBody PassengerDto passenger) {
		log.info("updatePassengerDetails() excecution - started");
		return ResponseEntity.ok(service.updatePassengerDetails(passenger));
	}

	@GetMapping("passeger/{mobileNumber}")
	public ResponseEntity<PassengerDto> getPassengerDetails(@PathVariable String mobileNumber) {
		log.info("getPassengerDetails() excecution - started");
		return ResponseEntity.ok(service.getPassengerDetails(mobileNumber));
	}

}