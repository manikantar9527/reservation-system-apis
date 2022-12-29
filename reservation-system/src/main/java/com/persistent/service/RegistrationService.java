package com.persistent.service;

import javax.validation.Valid;

import com.persistent.dto.PassengerDto;
import com.persistent.dto.StatusDto;

public interface RegistrationService {

	StatusDto addPassengerDetails(PassengerDto passenger);

	PassengerDto getPassengerDetails(String mobileNumber);

	StatusDto updatePassengerDetails(@Valid PassengerDto passenger);

}
