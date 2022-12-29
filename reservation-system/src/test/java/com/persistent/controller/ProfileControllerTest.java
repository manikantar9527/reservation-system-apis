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

import com.persistent.dto.PassengerDto;
import com.persistent.dto.StatusDto;
import com.persistent.service.RegistrationService;

@SpringBootTest
public class ProfileControllerTest {
	@Mock
	private RegistrationService service;

	@InjectMocks
	private ProfileController controller;

	@Test
	public void updatePassengerDetailsTest() throws Exception {
		when(service.addPassengerDetails(Mockito.any())).thenReturn(new StatusDto());
		ResponseEntity<StatusDto> responseEntity = controller.updatePassengerDetails(new PassengerDto());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void getPassengerDetailsTest() throws Exception {
		when(service.getPassengerDetails(Mockito.any())).thenReturn(new PassengerDto());
		ResponseEntity<PassengerDto> responseEntity = controller.getPassengerDetails("9499429529");
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
