package com.persistent.service;

import javax.validation.Valid;

import com.persistent.dto.CancelTicketDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TicketDto;

public interface TicketService {

	StatusDto cancelTicket(@Valid CancelTicketDto reqDto);

	StatusDto getTicketStatus(String pnr);

	TicketDto getTicketDetails(String mobileNumber);

}
