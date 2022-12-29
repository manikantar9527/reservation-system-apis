package com.persistent.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.dao.Ticket;
import com.persistent.dto.BookTicketDto;
import com.persistent.service.BookService;

@RestController
public class BookTicketController {

	@Autowired
	private BookService bookService;

	@PostMapping("book/ticket")
	public ResponseEntity<Ticket> bookTicket(@Valid @RequestBody BookTicketDto reqDto) {
		return ResponseEntity.ok(bookService.bookTicket(reqDto));
	}

}
