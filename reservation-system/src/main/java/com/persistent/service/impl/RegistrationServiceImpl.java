package com.persistent.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.persistent.config.AppConstants;
import com.persistent.dao.Passenger;
import com.persistent.dto.PassengerDto;
import com.persistent.exception.ReservationException;
import com.persistent.exception.Severity;
import com.persistent.repository.PassengerRepository;
import com.persistent.service.RegistrationService;

@Service
//@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PassengerDto addPassengerDetails(PassengerDto reqDto) {
		try {
			// log.info("addPassengerDetails request details" + reqDto);
			if (reqDto.getUserId() != null) {
				if (passengerRepository.findByUserIdOrContactNumber(reqDto.getUserId(),
						reqDto.getContactNumber()) == null) {
					// log.info(AppConstants.INVALID_MOBILENUMBER);
					throw new ReservationException(AppConstants.INVALID_MOBILENUMBER, HttpStatus.BAD_REQUEST,
							Severity.INFO);
				}
			}
			if (passengerRepository.findByContactNumber(reqDto.getContactNumber()) != null) {
				// log.info(AppConstants.USER_ALREADY_REGISTERED);
				throw new ReservationException(AppConstants.USER_ALREADY_REGISTERED, HttpStatus.BAD_REQUEST,
						Severity.INFO);
			}
			passengerRepository.save(modelMapper.map(reqDto, Passenger.class));
			return reqDto;
		} catch (Exception e) {
			throw new ReservationException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.INFO);
		}

	}

	@Override
	public PassengerDto getPassengerDetails(String ContactNumber) {
		Passenger passenger = passengerRepository.findByContactNumber(ContactNumber);
		if (passenger == null) {
			// log.info(AppConstants.INVALID_MOBILENUMBER);
			throw new ReservationException(AppConstants.INVALID_MOBILENUMBER, HttpStatus.BAD_REQUEST, Severity.INFO);
		}

		return modelMapper.map(passenger, PassengerDto.class);
	}

}
