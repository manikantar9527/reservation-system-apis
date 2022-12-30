
package com.persistent.cloudgateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

	@GetMapping("/reservationServiceFallBack")
	public ResponseEntity<String> userServiceFallBackMethod() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Reservation service taking longer than expected. Please try again later.");
	}

	@GetMapping("/bookTicketServiceFallBack")
	public ResponseEntity<String> depterviceFallBackMethod() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("BookTicket service taking longer than expected. Please try again later.");
	}
}
