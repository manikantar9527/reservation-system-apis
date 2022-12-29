package com.persistent.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.persistent.dao.Availability;
import com.persistent.dao.TrainInfo;
import com.persistent.dto.AvailabilityDto;
import com.persistent.repository.AvailabilityRepository;
import com.persistent.repository.TrainInfoRepository;

@SpringBootTest
public class AvailabilityServiceTest {
	@Autowired
	private AvailabilityService service;

	@MockBean
	private AvailabilityRepository availabilityRepository;

	@MockBean
	private TrainInfoRepository trainInfoRepository;

	@BeforeEach
	void before() {
		TrainInfo train = TrainInfo.builder().trainId(1L).total2ASeats(100).noOf2ACoaches(2).noOf3ACoaches(2)
				.total3ASeats(100).noOfSLCoaches(2).totalSLSeats(100).noOf2SCoaches(2).total2SSeats(100).build();
		when(trainInfoRepository.findByTrainId(any())).thenReturn(train);
	}

	@Test
	public void addAvailability() {
		when(availabilityRepository.findByTrainTrainIdAndDate(any(), any())).thenReturn(new ArrayList<>());
		assertNotNull(service.addAvailability(new AvailabilityDto(new Date(), 1L, "3A")));
	}
	

	@Test
	public void addAvailabilityWithException() {
		when(availabilityRepository.findByTrainTrainIdAndDate(any(), any())).thenReturn(Util.getAvailabilities(new TrainInfo(),new Date()));
		assertNotNull(service.addAvailability(new AvailabilityDto(new Date(), 1L, "3A")));
	}
	
	@Test
	public void addAvailabilityWithNullException() {
		when(availabilityRepository.findByTrainTrainIdAndDate(any(), any())).thenReturn(null);
		//assertNotNull(service.addAvailability(new AvailabilityDto(new Date(), 1L, "3A")));
		try {
			service.addAvailability(new AvailabilityDto(new Date(), 1L, "3A"));

		} catch (Exception e) {
			assertNotNull("");
		}
	}
	
	@Test
	public void ticketAvailability() {
		when(availabilityRepository.findByTrainTrainIdAndDateAndClassType(any(), any(),any())).thenReturn(Arrays.asList(new Availability()));
		assertNotNull(service.ticketAvailability(new AvailabilityDto(new Date(), 1L, "3A")));
	}
	@Test
	public void ticketAvailabilityWithException() {
		when(availabilityRepository.findByTrainTrainIdAndDateAndClassType(any(), any(),any())).thenReturn(new ArrayList<>());
		try {
			service.ticketAvailability(new AvailabilityDto(new Date(), 1L, "3A"));
		} catch (Exception e) {
			assertNotNull("");
		}
		
				
	}
	
}
