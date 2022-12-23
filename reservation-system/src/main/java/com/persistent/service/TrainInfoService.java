package com.persistent.service;

import java.util.List;

import com.persistent.dao.TrainInfo;
import com.persistent.dto.CancelTicketDto;
import com.persistent.dto.SearchTrainDto;
import com.persistent.dto.StatusDto;

public interface TrainInfoService {
	List<TrainInfo> searchTrain(SearchTrainDto reqDto);

	// Ticket bookTicket(BookTicketDto reqDto);

	StatusDto cancelTicket(CancelTicketDto reqDto);

	TrainInfo addTrainDetails(TrainInfo train);
}
