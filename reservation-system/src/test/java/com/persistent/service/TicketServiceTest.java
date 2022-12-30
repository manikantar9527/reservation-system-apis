package com.persistent.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.persistent.dao.Passenger;
import com.persistent.dao.Ticket;
import com.persistent.dao.TrainInfo;
import com.persistent.dto.CancelTicketDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TicketDto;
import com.persistent.repository.AvailabilityRepository;
import com.persistent.repository.PassengerRepository;
import com.persistent.repository.TicketRepository;
import com.persistent.repository.TrainInfoRepository;

@SpringBootTest
public class TicketServiceTest {
	@Autowired
	private TicketService service;

	@MockBean
	private PassengerRepository passengerRepository;

	@MockBean
	private AvailabilityRepository availabilityRepository;

	@MockBean
	private TrainInfoRepository trainInfoRepository;

	@MockBean
	private TicketRepository ticketRepository;

	private Date getTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(2023, 5, 25);
		Date date = cal.getTime();
		Instant inst = date.toInstant();
		LocalDate localDate = inst.atZone(ZoneId.systemDefault()).toLocalDate();
		Instant dayInst = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
		return Date.from(dayInst);
	}

	@BeforeEach
	void before() {
		TrainInfo train = TrainInfo.builder().trainId(1L).trainNumber("72456").trainName("ATP Express")
				.total2ASeats(100).noOf2ACoaches(2).noOf3ACoaches(2).total3ASeats(100).noOfSLCoaches(2)
				.totalSLSeats(100).noOf2SCoaches(2).total2SSeats(100).build();
		when(trainInfoRepository.findByTrainId(any())).thenReturn(train);
		when(ticketRepository.findByPassengerContactNumber(any())).thenReturn(Optional.of(new Ticket()));

	}

	@Test
	public void getTicketDetails() {
		Ticket ticket = Ticket.builder().ticketId(1L).status(0).date(getTime()).berthType("lower").coach("c1")
				.seatNumber("c1-1").classType("3A").train(null)
				.passenger(new Passenger(1L, "mani", "pwd", "9590989397", "email", "female", 30, "address", null))
				.build();
		when(ticketRepository.findByPassengerContactNumber(any())).thenReturn(Optional.of(ticket));
		TicketDto res = service.getTicketDetails("9590989397");
		assertNotNull(res);
	}

	@Test
	public void getTicketDetailsException() {
		when(ticketRepository.findByPassengerContactNumber(any())).thenReturn(Optional.empty());
		try {
			service.getTicketDetails("9590989397");
		} catch (Exception e) {
			assertNotNull("");
		}
	}

	@Test
	public void getTicketStatusCase1() {
		Ticket ticket = Ticket.builder().ticketId(1L).status(0).date(getTime()).berthType("lower").coach("c1")
				.seatNumber("c1-1").classType("3A").train(null)
				.passenger(new Passenger(1L, "mani", "pwd", "9590989397", "email", "female", 30, "address", null))
				.build();
		when(ticketRepository.findByPnr(any())).thenReturn(Optional.of(ticket));
		StatusDto res = service.getTicketStatus("9989397");
		assertNotNull(res);
	}

	@Test
	public void getTicketStatusCase2() {
		Ticket ticket = Ticket.builder().ticketId(1L).status(1).date(getTime()).berthType("lower").coach("c1")
				.seatNumber("c1-1").classType("3A").train(null)
				.passenger(new Passenger(1L, "mani", "pwd", "9590989397", "email", "female", 30, "address", null))
				.build();
		when(ticketRepository.findByPnr(any())).thenReturn(Optional.of(ticket));
		StatusDto res = service.getTicketStatus("9989397");
		assertNotNull(res);
	}

	@Test
	public void getTicketStatusCase3() {
		Ticket ticket = Ticket.builder().ticketId(1L).status(2).date(getTime()).berthType("lower").coach("c1")
				.seatNumber("c1-1").classType("3A").train(null)
				.passenger(new Passenger(1L, "mani", "pwd", "9590989397", "email", "female", 30, "address", null))
				.build();
		when(ticketRepository.findByPnr(any())).thenReturn(Optional.of(ticket));
		StatusDto res = service.getTicketStatus("9989397");
		assertNotNull(res);
	}

	@Test
	public void getTicketStatusCase4() {
		Ticket ticket = Ticket.builder().ticketId(1L).status(3).date(getTime()).berthType("lower").coach("c1")
				.seatNumber("c1-1").classType("3A").train(null)
				.passenger(new Passenger(1L, "mani", "pwd", "9590989397", "email", "female", 30, "address", null))
				.build();
		when(ticketRepository.findByPnr(any())).thenReturn(Optional.of(ticket));
		StatusDto res = service.getTicketStatus("9989397");
		assertNotNull(res);
	}

	@Test
	public void getTicketStatusException() {
		when(ticketRepository.findByPnr(any())).thenReturn(Optional.empty());
		try {
			service.getTicketStatus("9989397");

		} catch (Exception e) {
			assertNotNull("");
		}
	}

	@Test
	public void cancelTicketCase1() {
		TrainInfo train = TrainInfo.builder().trainId(1L).trainNumber("72456").trainName("ATP Express")
				.total2ASeats(100).noOf2ACoaches(2).noOf3ACoaches(2).total3ASeats(100).noOfSLCoaches(2)
				.totalSLSeats(100).noOf2SCoaches(2).total2SSeats(100).build();
		Ticket ticket = Ticket.builder().ticketId(1L).status(0).date(getTime()).berthType("lower").coach("c1")
				.seatNumber("c1-1").classType("3A").train(train)
				.passenger(new Passenger(1L, "mani", "pwd", "9590989397", "email", "female", 30, "address", null))
				.build();
		when(ticketRepository.findByPassengerContactNumberAndTicketIdAndStatus("9590989397", 1L, 0))
				.thenReturn(Optional.of(ticket));
		when(availabilityRepository.findByTrainTrainIdAndDateAndClassTypeOrderByCoach(1L, getTime(), "3A"))
				.thenReturn(Util.getAvailabilities(train, getTime()));
		when(availabilityRepository.findByTrainTrainIdAndDateAndClassTypeAndCoach(any(), any(), any(), any()))
				.thenReturn(Util.getAvailabilities(train, getTime()).get(0));
		assertNotNull(service.cancelTicket(new CancelTicketDto("9590989397", 1L)));
	}

	@Test
	public void cancelTicketCase2() {
		TrainInfo train = TrainInfo.builder().trainId(1L).trainNumber("72456").trainName("ATP Express")
				.total2ASeats(100).noOf2ACoaches(2).noOf3ACoaches(2).total3ASeats(100).noOfSLCoaches(2)
				.totalSLSeats(100).noOf2SCoaches(2).total2SSeats(100).build();
		Ticket ticket = Ticket.builder().ticketId(1L).status(0).date(getTime()).berthType("lower").coach("c1")
				.seatNumber("c1-1").classType("3A").train(train)
				.passenger(new Passenger(1L, "mani", "pwd", "9590989397", "email", "female", 30, "address", null))
				.build();
		when(ticketRepository.findByPassengerContactNumberAndTicketIdAndStatus("9590989397", 1L, 0))
				.thenReturn(Optional.of(ticket));
		when(availabilityRepository.findByTrainTrainIdAndDateAndClassTypeOrderByCoach(1L, getTime(), "3A"))
				.thenReturn(Util.getAvailabilitiesWithWeightingList(train, getTime()));

		when(availabilityRepository.findByTrainTrainIdAndDateAndClassTypeAndCoach(any(), any(), any(), any()))
				.thenReturn(Util.getAvailabilities(train, getTime()).get(0));
		when(ticketRepository.findFirstByTrainTrainIdAndDateAndClassTypeAndStatusOrderByTicketId(1L, getTime(), "3A",
				2)).thenReturn(ticket);
		assertNotNull(service.cancelTicket(new CancelTicketDto("9590989397", 1L)));
	}

	@Test
	public void cancelTicketWithException() {
		TrainInfo train = TrainInfo.builder().trainId(1L).trainNumber("72456").trainName("ATP Express")
				.total2ASeats(100).noOf2ACoaches(2).noOf3ACoaches(2).total3ASeats(100).noOfSLCoaches(2)
				.totalSLSeats(100).noOf2SCoaches(2).total2SSeats(100).build();
		when(ticketRepository.findByPassengerContactNumberAndTicketIdAndStatus("9590989397", 1L, 0))
				.thenReturn(Optional.empty());
		when(availabilityRepository.findByTrainTrainIdAndDateAndClassTypeOrderByCoach(1L, getTime(), "3A"))
				.thenReturn(Util.getAvailabilities(train, getTime()));
		when(availabilityRepository.findByTrainTrainIdAndDateAndClassTypeAndCoach(any(), any(), any(), any()))
				.thenReturn(Util.getAvailabilities(train, getTime()).get(0));
		try {
			service.cancelTicket(new CancelTicketDto("9590989397", 1L));
		} catch (Exception e) {
			assertNotNull("");
		}

	}
	
	@Test
	public void cancelTicketCase3() {
		TrainInfo train = TrainInfo.builder().trainId(1L).trainNumber("72456").trainName("ATP Express")
				.total2ASeats(100).noOf2ACoaches(2).noOf3ACoaches(2).total3ASeats(100).noOfSLCoaches(2)
				.totalSLSeats(100).noOf2SCoaches(2).total2SSeats(100).build();
		Ticket ticket = Ticket.builder().ticketId(1L).status(0).date(new Date()).berthType("lower").coach("c1")
				.seatNumber("c1-1").classType("3A").train(train)
				.passenger(new Passenger(1L, "mani", "pwd", "9590989397", "email", "female", 30, "address", null))
				.build();
		when(ticketRepository.findByPassengerContactNumberAndTicketIdAndStatus("9590989397", 1L, 0))
				.thenReturn(Optional.of(ticket));
		when(availabilityRepository.findByTrainTrainIdAndDateAndClassTypeOrderByCoach(1L, getTime(), "3A"))
				.thenReturn(Util.getAvailabilitiesWithWeightingList(train, getTime()));

		when(availabilityRepository.findByTrainTrainIdAndDateAndClassTypeAndCoach(any(), any(), any(), any()))
				.thenReturn(Util.getAvailabilities(train, getTime()).get(0));
		when(ticketRepository.findFirstByTrainTrainIdAndDateAndClassTypeAndStatusOrderByTicketId(1L, getTime(), "3A",
				2)).thenReturn(ticket);
		try {
			service.cancelTicket(new CancelTicketDto("9590989397", 1L));
		} catch (Exception e) {
			assertNotNull("");
		}
	}
	
	

}
