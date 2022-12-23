package com.persistent.service;

import com.persistent.dto.PassengerDto;

public interface RegistrationService {

	PassengerDto addPassengerDetails(PassengerDto passenger);

	PassengerDto getPassengerDetails(String mobileNumber);

}
