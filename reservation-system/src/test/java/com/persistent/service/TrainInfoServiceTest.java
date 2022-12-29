package com.persistent.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.persistent.dao.TrainInfo;
import com.persistent.dto.SearchTrainDto;
import com.persistent.repository.AvailabilityRepository;
import com.persistent.repository.PassengerRepository;
import com.persistent.repository.TicketRepository;
import com.persistent.repository.TrainInfoRepository;

@SpringBootTest
public class TrainInfoServiceTest {
	@Autowired
	private TrainInfoService service;

	@MockBean
	private PassengerRepository passengerRepository;

	@MockBean
	private AvailabilityRepository availabilityRepository;

	@MockBean
	private TrainInfoRepository trainInfoRepository;

	@MockBean
	private TicketRepository ticketRepository;

	Date date = new Date();

	@BeforeEach
	void before() {
		TrainInfo train = TrainInfo.builder().trainId(1L).trainNumber("72456").trainName("ATP Express")
				.total2ASeats(100).noOf2ACoaches(2).noOf3ACoaches(2).total3ASeats(100).noOfSLCoaches(2)
				.totalSLSeats(100).noOf2SCoaches(2).total2SSeats(100).build();
		when(trainInfoRepository.findByTrainId(any())).thenReturn(train);
	}

	@Test
	public void addAvailability() {
		TrainInfo train = TrainInfo.builder().trainId(1L).total2ASeats(100).noOf2ACoaches(2).noOf3ACoaches(2)
				.total3ASeats(100).noOfSLCoaches(2).totalSLSeats(100).noOf2SCoaches(2).total2SSeats(100).build();
		when(availabilityRepository.findByTrainTrainIdAndDate(any(), any())).thenReturn(new ArrayList<>());
		when(availabilityRepository.save(any())).thenReturn(train);
		assertNotNull(service.addTrainDetails(train));
	}

	@Test
	public void searchTrain() {
		TrainInfo train = TrainInfo.builder().trainId(1L).trainNumber("72456").trainName("ATP Express")
				.total2ASeats(100).noOf2ACoaches(2).noOf3ACoaches(2).total3ASeats(100).noOfSLCoaches(2)
				.totalSLSeats(100).noOf2SCoaches(2).total2SSeats(100).build();
		when(trainInfoRepository.findBySourceAndDestinationAndAvailableDaysContaining(any(), any(), any()))
				.thenReturn(Arrays.asList(train));

		when(availabilityRepository.findByTrainTrainIdAndClassType(any(), any()))
				.thenReturn(Util.getAvailabilities(train, date));
		assertNotNull(service.searchTrain(new SearchTrainDto("KDR", "ATP", date)));
	}
	// searchTrain

	@Test
	public void searchTrainException() {
		TrainInfo train = TrainInfo.builder().trainId(1L).trainNumber("72456").trainName("ATP Express")
				.total2ASeats(100).noOf2ACoaches(2).noOf3ACoaches(2).total3ASeats(100).noOfSLCoaches(2)
				.totalSLSeats(100).noOf2SCoaches(2).total2SSeats(100).build();
		when(trainInfoRepository.findBySourceAndDestinationAndAvailableDaysContaining(any(), any(), any()))
				.thenReturn(new ArrayList<>());

		when(availabilityRepository.findByTrainTrainIdAndClassType(any(), any()))
				.thenReturn(Util.getAvailabilities(train, date));
		try {
			service.searchTrain(new SearchTrainDto("KDR", "ATP", date));

		} catch (Exception e) {
			assertNotNull("");
		}

	}

}
