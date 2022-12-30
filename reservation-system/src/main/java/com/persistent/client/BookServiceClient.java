package com.persistent.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.persistent.dao.Ticket;
import com.persistent.dto.BookTicketDto;

@FeignClient(name = "book-ticket")
public interface BookServiceClient {

	@PostMapping("book/ticket")
	public ResponseEntity<Ticket> bookTicket(@RequestBody BookTicketDto reqDto);
}
