package com.persistent.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.persistent.dao.Availability;
import com.persistent.dto.AvailabilityDto;

@FeignClient(name = "reservation-service")
public interface ReservationSystemClient {


	@PostMapping("reservation/rest/ticket/availability")
	public ResponseEntity<List<Availability>> ticketAvailability(@RequestBody AvailabilityDto reqDto);

}
