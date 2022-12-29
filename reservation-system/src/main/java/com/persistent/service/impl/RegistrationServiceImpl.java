package com.persistent.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.persistent.config.AppConstants;
import com.persistent.dao.Passenger;
import com.persistent.dto.PassengerDto;
import com.persistent.dto.StatusDto;
import com.persistent.exception.ReservationException;
import com.persistent.exception.Severity;
import com.persistent.repository.PassengerRepository;
import com.persistent.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public StatusDto addPassengerDetails(PassengerDto reqDto) {
		try {
			log.info("addPassengerDetails request details" + reqDto);
			Optional<Passenger> passenger = passengerRepository.findByContactNumber(reqDto.getContactNumber());
			if (passenger.isPresent())
				throw new ReservationException(AppConstants.USER_ALREADY_REGISTERED, HttpStatus.PRECONDITION_FAILED,
						Severity.INFO);
			passengerRepository.save(modelMapper.map(reqDto, Passenger.class));
			return new StatusDto(1, AppConstants.USER_DETAILS_ADDED);
		} catch (Exception e) {
			throw new ReservationException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.ERROR);
		}

	}

	@Override
	public StatusDto updatePassengerDetails(PassengerDto reqDto) {
		try {
			log.info("addPassengerDetails request details" + reqDto);
			if (reqDto.getContactNumber() != null)
				passengerRepository.findByContactNumber(reqDto.getContactNumber())
						.orElseThrow(() -> new ReservationException(AppConstants.INVALID_MOBILENUMBER,
								HttpStatus.PRECONDITION_FAILED, Severity.INFO));

			passengerRepository.save(modelMapper.map(reqDto, Passenger.class));
			return new StatusDto(1, AppConstants.USER_DETAILS_UPDATED);
		} catch (Exception e) {
			throw new ReservationException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.ERROR);
		}

	}

	@Override
	public PassengerDto getPassengerDetails(String ContactNumber) {
		try {
			Passenger passenger = passengerRepository.findByContactNumber(ContactNumber)
					.orElseThrow(() -> new ReservationException(AppConstants.INVALID_MOBILENUMBER,
							HttpStatus.PRECONDITION_FAILED, Severity.INFO));
			return modelMapper.map(passenger, PassengerDto.class);
		} catch (Exception e) {
			throw new ReservationException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.ERROR);
		}
	}

}
