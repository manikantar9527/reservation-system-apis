package com.persistent.service;

import java.util.List;

import com.persistent.dao.Availability;
import com.persistent.dto.AvailabilityDto;
import com.persistent.dto.StatusDto;

public interface AvailabilityService {
	StatusDto addAvailability(AvailabilityDto reqDto);

	List<Availability> ticketAvailability(AvailabilityDto reqDto);
}
