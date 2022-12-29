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

import com.persistent.dao.TrainInfo;
import com.persistent.dto.CancelTicketDto;
import com.persistent.dto.SearchTrainDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TrainAvailabilityDto;
import com.persistent.service.TrainInfoService;

@SpringBootTest
public class TrainInfoControllerTest {
	@Mock
	private TrainInfoService service;

	@InjectMocks
	private TrainInfoController controller;

	@Test
	public void addTrainDetailsTest() throws Exception {
		when(service.addTrainDetails(Mockito.any())).thenReturn(new StatusDto());
		ResponseEntity<StatusDto> responseEntity = controller.addTrainDetails(new TrainInfo());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void cancelTicketTest() throws Exception {
		when(service.cancelTicket(Mockito.any())).thenReturn(new StatusDto());
		ResponseEntity<StatusDto> responseEntity = controller.cancelTicket(new CancelTicketDto());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void searchTrainTest() throws Exception {
		when(service.searchTrain(Mockito.any())).thenReturn(new ArrayList<TrainAvailabilityDto>());
		ResponseEntity<List<TrainAvailabilityDto>> responseEntity = controller.searchTrain(new SearchTrainDto());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
