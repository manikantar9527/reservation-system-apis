package com.persistent.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.persistent.dto.StatusDto;
import com.persistent.service.AvailabilityService;

@SpringBootTest
public class AvailabilityControllerTest {
	@Mock
	private AvailabilityService service;

	@InjectMocks
	private AvailabilityController controller;

	@Test
	public void addAvailabilityTest() throws Exception {
		when(service.addAvailability(Mockito.any())).thenReturn(new StatusDto());
		ResponseEntity<StatusDto> responseEntity = controller.addAvailability(new AvailabilityDto());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void ticketAvailabilityTest() throws Exception {
		when(service.ticketAvailability(Mockito.any())).thenReturn(new ArrayList<Availability>());
		ResponseEntity<List<Availability>> responseEntity = controller.ticketAvailability(new AvailabilityDto());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}
