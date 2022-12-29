package com.persistent.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.persistent.config.AppConstants;
import com.persistent.dao.Availability;
import com.persistent.dao.Ticket;
import com.persistent.dao.TrainInfo;
import com.persistent.dto.CancelTicketDto;
import com.persistent.dto.SearchTrainDto;
import com.persistent.dto.SeatInfoDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TicketStatus;
import com.persistent.dto.TrainAvailabilityDto;
import com.persistent.exception.ReservationException;
import com.persistent.exception.Severity;
import com.persistent.repository.AvailabilityRepository;
import com.persistent.repository.TicketRepository;
import com.persistent.repository.TrainInfoRepository;
import com.persistent.service.TrainInfoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainInfoServiceImpl implements TrainInfoService {

	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Autowired
	private TrainInfoRepository trainInfoRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@SuppressWarnings("deprecation")
	@Override
	public List<TrainAvailabilityDto> searchTrain(SearchTrainDto reqDto) {
		List<TrainInfo> list = trainInfoRepository.findBySourceAndDestinationAndAvailableDaysContaining(
				reqDto.getSource(), reqDto.getDestination(), String.valueOf(reqDto.getDate().getDay()));
		if (list.isEmpty())
			throw new ReservationException(AppConstants.TRAIN_NOT_AVAILABLE, HttpStatus.PRECONDITION_FAILED,
					Severity.INFO);
		List<TrainAvailabilityDto> trains = new ArrayList<>();
		list.forEach(t -> {
			TrainAvailabilityDto trainDto = new TrainAvailabilityDto();
			trainDto.setTrainId(t.getTrainId());
			trainDto.setTrainName(t.getTrainName());
			trainDto.setTrainNumber(t.getTrainNumber());
			List<SeatInfoDto> availabilities = new ArrayList<>();
			List<String> classTypes = Arrays.asList("3A", "2A", "SL", "2S");
			classTypes.forEach(c -> {
				List<Availability> availableSeats = availabilityRepository
						.findByTrainTrainIdAndClassType(t.getTrainId(), c);
				SeatInfoDto seatInfoDto = new SeatInfoDto();
				seatInfoDto.setClassType(c);
				seatInfoDto.setNoOfSeats(
						availableSeats.stream().collect(Collectors.summingInt(Availability::getNoOfLowerSeatsAvailable))
								+ availableSeats.stream()
										.collect(Collectors.summingInt(Availability::getNoOfUpperSeatsAvailable)));
				availabilities.add(seatInfoDto);
			});
			trainDto.setSeatInfoList(availabilities);
			trains.add(trainDto);
		});

		return trains;
	}
	// train not avaibale b/w stations

	@Override
	public StatusDto cancelTicket(CancelTicketDto reqDto) {
		try {
			log.info("cancelTicket request details" + reqDto);
			Ticket ticket = ticketRepository
					.findByPassengerContactNumberAndTicketIdAndStatus(reqDto.getContactNumber(), reqDto.getTicketId(),
							TicketStatus.CONFORMED.getValue())
					.orElseThrow(() -> new ReservationException(AppConstants.INVALID_TICKET_DETAILS,
							HttpStatus.PRECONDITION_FAILED, Severity.INFO));

			ticket.setStatus(TicketStatus.CANCELLED.getValue());
			ticketRepository.save(ticket);
			List<Availability> availabilities = availabilityRepository
					.findByTrainTrainIdAndDateAndClassTypeOrderByCoach(ticket.getTrain().getTrainId(), ticket.getDate(),
							ticket.getClassType());
			int totalSeatsAvailable = availabilities.stream()
					.collect(Collectors.summingInt(Availability::getNoOfLowerSeatsAvailable))
					+ availabilities.stream().collect(Collectors.summingInt(Availability::getNoOfUpperSeatsAvailable));
			Availability avail = availabilities.get(0);
			if (totalSeatsAvailable == 0 && (avail.getLowerWaitingList() + avail.getUpperWaitingList()) != 0) {
				Ticket ticketToConfirm = ticketRepository
						.findFirstByTrainTrainIdAndDateAndClassTypeAndStatusAndBerthTypeOrderByTicketId(
								ticket.getTrain().getTrainId(), ticket.getDate(), ticket.getClassType(), 2,
								ticket.getBerthType());
				if (ticketToConfirm == null)
					ticketToConfirm = ticketRepository
							.findFirstByTrainTrainIdAndDateAndClassTypeAndStatusOrderByTicketId(
									ticket.getTrain().getTrainId(), ticket.getDate(), ticket.getClassType(), 2);

				ticketToConfirm.setSeatNumber(ticket.getSeatNumber());
				ticketToConfirm.setCoach(ticket.getCoach());
				ticketToConfirm.setStatus(TicketStatus.CONFORMED.getValue());
				ticketRepository.save(ticketToConfirm);
			} else {
				Availability availability = availabilityRepository.findByTrainTrainIdAndDateAndClassTypeAndCoach(
						ticket.getTrain().getTrainId(), ticket.getDate(), ticket.getClassType(), ticket.getCoach());
				if (AppConstants.LOWER.equalsIgnoreCase(ticket.getBerthType()))
					availability.setNoOfLowerSeatsAvailable(availability.getNoOfLowerSeatsAvailable() + 1);
				else
					availability.setNoOfUpperSeatsAvailable(availability.getNoOfUpperSeatsAvailable() + 1);

				availabilityRepository.save(availability);
			}

			log.info(AppConstants.TICKET_CANCELLED_SUCCESSFULLY);
			return new StatusDto(1, AppConstants.TICKET_CANCELLED_SUCCESSFULLY);
		} catch (Exception e) {
			throw new ReservationException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.INFO);
		}
	}

	@Override
	public StatusDto addTrainDetails(TrainInfo trainInfo) {
		trainInfoRepository.save(trainInfo);
		return new StatusDto(1, AppConstants.TRAIN_DETAILS_ADDED);
	}
}
