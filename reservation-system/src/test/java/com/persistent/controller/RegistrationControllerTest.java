package com.persistent.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.persistent.dao.Passenger;
import com.persistent.dto.PassengerDto;
import com.persistent.dto.StatusDto;
import com.persistent.service.RegistrationService;

@SpringBootTest
public class RegistrationControllerTest {
	@Mock
	private RegistrationService service;

	@InjectMocks
	private RegistrationController controller;

	@Test
	public void addPassengerDetailsTest() throws Exception {
		when(service.addPassengerDetails(Mockito.any())).thenReturn(new StatusDto());
		ResponseEntity<StatusDto> responseEntity = controller.addPassengerDetails(new PassengerDto());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
