package com.persistent.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.dao.Availability;
import com.persistent.dto.AvailabilityDto;
import com.persistent.dto.StatusDto;
import com.persistent.service.AvailabilityService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("rest")
public class AvailabilityController {

	@Autowired
	private AvailabilityService service;

	@PostMapping("add/availability")
	public ResponseEntity<StatusDto> addAvailability(@Valid @RequestBody AvailabilityDto reqDto) {
		log.info("addAvailability() excecution - started");
		return ResponseEntity.ok(service.addAvailability(reqDto));
	}

	@PostMapping("ticket/availability")
	public ResponseEntity<List<Availability>> ticketAvailability(@Valid @RequestBody AvailabilityDto reqDto) {
		log.info("ticketAvailability() excecution - started");
		return ResponseEntity.ok(service.ticketAvailability(reqDto));
	}
}
