package com.persistent.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.dto.PassengerDto;
import com.persistent.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RegistrationController {

	@Autowired
	private RegistrationService service;

	@PostMapping("passeger/registration")
	public ResponseEntity<PassengerDto> addPassengerDetails(@Valid @RequestBody PassengerDto passenger) {
		log.info("addPassengerDetails() excecution - started");
		return ResponseEntity.ok(service.addPassengerDetails(passenger));
	}

}
