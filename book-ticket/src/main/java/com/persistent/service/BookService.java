package com.persistent.service;

import com.persistent.dao.Ticket;
import com.persistent.dto.BookTicketDto;

public interface BookService {
	Ticket bookTicket(BookTicketDto reqDto);

}
