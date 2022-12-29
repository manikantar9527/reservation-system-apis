package com.persistent.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.persistent.dao.Availability;
import com.persistent.dto.AvailabilityDto;
import com.persistent.dto.PassengerDto;
import com.persistent.dto.StatusDto;
import com.persistent.service.AvailabilityService;
import com.persistent.service.RegistrationService;

@SpringBootTest
public class RegistrationControllerTest {
	@Mock
	private RegistrationService service;

	@InjectMocks
	private RegistrationController controller;

	@Mock
	private AvailabilityService availabilityService;

	@Test
	public void addPassengerDetailsTest() throws Exception {
		when(service.addPassengerDetails(Mockito.any())).thenReturn(new StatusDto());
		ResponseEntity<StatusDto> responseEntity = controller
				.addPassengerDetails(new PassengerDto(1L, "mani", "", "", "", 5, "", "", null));
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void ticketAvailability() throws Exception {
		when(availabilityService.ticketAvailability(Mockito.any())).thenReturn(new ArrayList<Availability>());
		ResponseEntity<List<Availability>> responseEntity = controller
				.ticketAvailability(new AvailabilityDto(new Date(), 1L, null));
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
