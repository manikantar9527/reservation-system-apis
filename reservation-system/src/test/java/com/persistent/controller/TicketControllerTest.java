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

import com.persistent.dto.CancelTicketDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TicketDto;
import com.persistent.service.TicketService;

@SpringBootTest
public class TicketControllerTest {
	@Mock
	private TicketService service;

	@InjectMocks
	private TicketController controller;

	@Test
	public void cancelTicketTest() throws Exception {
		when(service.cancelTicket(Mockito.any())).thenReturn(new StatusDto());
		ResponseEntity<StatusDto> responseEntity = controller.cancelTicket(new CancelTicketDto());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void getTicketDetails() throws Exception {
		when(service.getTicketDetails(Mockito.any())).thenReturn(new TicketDto());
		ResponseEntity<TicketDto> responseEntity = controller.getTicketDetails("9499429529");
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void getTicketStatus() throws Exception {
		when(service.getTicketStatus(Mockito.any())).thenReturn(new StatusDto());
		ResponseEntity<StatusDto> responseEntity = controller.getTicketStatus("9499429529");
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}