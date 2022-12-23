package com.persistent.service;

import com.persistent.dao.Ticket;
import com.persistent.dto.BookTicketDto;

public interface ReservationService {
	Ticket bookTicket(BookTicketDto reqDto);

}
