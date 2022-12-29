package com.persistent.service;

import java.util.List;

import com.persistent.dao.TrainInfo;
import com.persistent.dto.CancelTicketDto;
import com.persistent.dto.SearchTrainDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TrainAvailabilityDto;

public interface TrainInfoService {
	List<TrainAvailabilityDto> searchTrain(SearchTrainDto reqDto);

	// Ticket bookTicket(BookTicketDto reqDto);

	StatusDto cancelTicket(CancelTicketDto reqDto);

	StatusDto addTrainDetails(TrainInfo train);
}
