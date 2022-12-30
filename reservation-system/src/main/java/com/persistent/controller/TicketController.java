package com.persistent.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.dao.Ticket;
import com.persistent.dto.BookTicketDto;
import com.persistent.dto.CancelTicketDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TicketDto;
import com.persistent.service.TicketService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("rest")
public class TicketController {

	@Autowired
	private TicketService service;

	@PostMapping("cancel/ticket")
	public ResponseEntity<StatusDto> cancelTicket(@Valid @RequestBody CancelTicketDto reqDto) {
		log.info("cancelTicket() excecution - started");
		return ResponseEntity.ok(service.cancelTicket(reqDto));
	}

	@GetMapping("ticket/status/{pnr}")
	public ResponseEntity<StatusDto> getTicketStatus(@PathVariable String pnr) {
		log.info("getTicketStatus() excecution - started");
		return ResponseEntity.ok(service.getTicketStatus(pnr));
	}

	@GetMapping("ticket/details/{mobileNumber}")
	public ResponseEntity<List<TicketDto>> getTicketDetails(@PathVariable String mobileNumber) {
		log.info("getTicketStatus() excecution - started");
		return ResponseEntity.ok(service.getTicketDetails(mobileNumber));
	}

	@PostMapping("book/ticket")
	public ResponseEntity<Ticket> bookTicket(@Valid @RequestBody BookTicketDto reqDto) {
		log.info("bookTicket() excecution - started");
		return ResponseEntity.ok(service.bookTicket(reqDto));
	}

}
