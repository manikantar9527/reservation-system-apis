package com.persistent.service;

import java.util.List;

import javax.validation.Valid;

import com.persistent.dao.Ticket;
import com.persistent.dto.BookTicketDto;
import com.persistent.dto.CancelTicketDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TicketDto;

public interface TicketService {

	StatusDto cancelTicket(@Valid CancelTicketDto reqDto);

	StatusDto getTicketStatus(String pnr);

	List<TicketDto> getTicketDetails(String mobileNumber);

	Ticket bookTicket(@Valid BookTicketDto reqDto);

}
