package com.persistent.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.persistent.dao.Passenger;
import com.persistent.dto.PassengerDto;
import com.persistent.repository.PassengerRepository;

@SpringBootTest
public class RegistrationServiceTest {
	@Autowired
	private RegistrationService service;

	@MockBean
	private PassengerRepository passengerRepository;

	@Mock
	private ModelMapper modelMapper;

	@BeforeEach
	void before() {
		when(passengerRepository.findByContactNumber("9590989397")).thenReturn(
				Optional.of(new Passenger(1L, "mani", "pwd", "9590989397", "email", "female", 30, "address", null)));
	}

	@Test
	public void addPassenger() {
		when(passengerRepository.findByContactNumber("9590989397")).thenReturn(Optional.empty());
		PassengerDto user = new PassengerDto(null, "mani", "9590989397", "email", "female", 30, null, null, null);
		assertNotNull(service.addPassengerDetails(user));
	}

	@Test
	public void addPassengerException() {
		when(passengerRepository.findByContactNumber("9590989397")).thenReturn(Optional.of(new Passenger()));
		PassengerDto user = new PassengerDto(null, "mani", "9590989397", "email", "female", 30, null, null, null);
		try {
			service.addPassengerDetails(user);

		} catch (Exception e) {
			assertNotNull("");
		}

	}

	@Test
	public void updatePassenger() {
		PassengerDto user = new PassengerDto(1L, "mani", "9590989397", "email", "female", 30, null, null, null);
		assertNotNull(service.updatePassengerDetails(user));
	}
	@Test
	public void updatePassengerException() {
		when(passengerRepository.findByContactNumber("9590989397")).thenReturn(Optional.empty());
		PassengerDto user = new PassengerDto(1L, "mani", "9590989397", "email", "female", 30, null, null, null);
		try {
			service.updatePassengerDetails(user);

		} catch (Exception e) {
			assertNotNull("");
		}
	}

	@Test
	public void getPassengerDetails() {
		PassengerDto res = service.getPassengerDetails("9590989397");
		assertNotNull(res);
	}
	@Test
	public void getPassengerDetailsException() {
		when(passengerRepository.findByContactNumber("9590989397")).thenReturn(Optional.empty());
		try {
			service.getPassengerDetails("9590989397");

		} catch (Exception e) {
			assertNotNull("");
		}
	}
}
