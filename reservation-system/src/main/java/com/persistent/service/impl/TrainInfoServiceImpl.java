package com.persistent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.persistent.config.AppConstants;
import com.persistent.dao.Availability;
import com.persistent.dao.Ticket;
import com.persistent.dao.TrainInfo;
import com.persistent.dto.CancelTicketDto;
import com.persistent.dto.SearchTrainDto;
import com.persistent.dto.StatusDto;
import com.persistent.exception.ReservationException;
import com.persistent.exception.Severity;
import com.persistent.repository.AvailabilityRepository;
import com.persistent.repository.TicketRepository;
import com.persistent.repository.TrainInfoRepository;
import com.persistent.service.TrainInfoService;

@Service
//@Slf4j
public class TrainInfoServiceImpl implements TrainInfoService {

	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Autowired
	private TrainInfoRepository trainInfoRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@SuppressWarnings("deprecation")
	@Override
	public List<TrainInfo> searchTrain(SearchTrainDto reqDto) {
		List<TrainInfo> list = trainInfoRepository.findBySourceAndDestinationAndAvailableDaysContaining(
				reqDto.getSource(), reqDto.getDestination(), String.valueOf(reqDto.getDate().getDay()));
		if (list.isEmpty())
			throw new ReservationException(AppConstants.INVALID_DETAILS, HttpStatus.BAD_REQUEST, Severity.INFO);
		else
			return list;
	}

	@Override
	public StatusDto cancelTicket(CancelTicketDto reqDto) {
		try {
			// log.info("cancelTicket request details" + reqDto);
			Ticket ticket = ticketRepository.findByPassengerContactNumberAndTicketIdAndStatus(reqDto.getContactNumber(),
					reqDto.getTicketId(), 0);
			if (ticket == null) {
				// log.info(AppConstants.INVALID_TICKET_DETAILS);
				return new StatusDto(1, AppConstants.INVALID_TICKET_DETAILS);
			}
			ticket.setStatus(1);
			ticketRepository.save(ticket);
			Availability availability = availabilityRepository.findByTrainTrainIdAndDateAndCoach(
					ticket.getTrain().getTrainId(), ticket.getDate(), ticket.getCoach());
			if (AppConstants.LOWER.equalsIgnoreCase(ticket.getBerthType()))
				availability.setNoOfLowerSeatsAvailable(availability.getNoOfLowerSeatsAvailable() + 1);
			else
				availability.setNoOfUpperSeatsAvailable(availability.getNoOfUpperSeatsAvailable() + 1);

			availabilityRepository.save(availability);

			// log.info(AppConstants.TICKET_CANCELLED_SUCCESSFULLY);
			return new StatusDto(1, AppConstants.TICKET_CANCELLED_SUCCESSFULLY);
		} catch (Exception e) {
			throw new ReservationException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.INFO);
		}
	}

	@Override
	public TrainInfo addTrainDetails(TrainInfo trainInfo) {
		TrainInfo info = trainInfoRepository.save(trainInfo);
		if (info == null)
			throw new ReservationException(AppConstants.INVALID_DETAILS, HttpStatus.BAD_REQUEST, Severity.INFO);
		else
			return info;
	}
}
