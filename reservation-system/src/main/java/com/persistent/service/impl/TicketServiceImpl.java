package com.persistent.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.persistent.config.AppConstants;
import com.persistent.dao.Availability;
import com.persistent.dao.Ticket;
import com.persistent.dto.CancelTicketDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TicketDto;
import com.persistent.dto.TicketStatus;
import com.persistent.exception.ReservationException;
import com.persistent.exception.Severity;
import com.persistent.repository.AvailabilityRepository;
import com.persistent.repository.TicketRepository;
import com.persistent.service.TicketService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TicketServiceImpl implements TicketService {

	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public StatusDto cancelTicket(CancelTicketDto reqDto) {
		try {
			log.info("cancelTicket request details" + reqDto);
			Ticket ticket = ticketRepository
					.findByPassengerContactNumberAndTicketIdAndStatus(reqDto.getContactNumber(), reqDto.getTicketId(),
							TicketStatus.CONFORMED.getValue())
					.orElseThrow(() -> new ReservationException(AppConstants.INVALID_TICKET_DETAILS,
							HttpStatus.PRECONDITION_FAILED, Severity.INFO));
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(new Date());
			cal2.setTime(ticket.getDate());
			if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
					&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
				throw new ReservationException(AppConstants.TICKET_CANCELLATION_NOT_ALLOWED,
						HttpStatus.PRECONDITION_FAILED, Severity.INFO);
			 

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
								ticket.getTrain().getTrainId(), ticket.getDate(), ticket.getClassType(), TicketStatus.WAITINGLIST.getValue(),
								ticket.getBerthType());
				if (ticketToConfirm == null)
					ticketToConfirm = ticketRepository
							.findFirstByTrainTrainIdAndDateAndClassTypeAndStatusOrderByTicketId(
									ticket.getTrain().getTrainId(), ticket.getDate(), ticket.getClassType(), TicketStatus.WAITINGLIST.getValue());

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
	public StatusDto getTicketStatus(String pnr) {
		try {
			StatusDto response = new StatusDto();
			response.setCode(0);
			Ticket ticket = ticketRepository.findByPnr(pnr)
					.orElseThrow(() -> new ReservationException(AppConstants.INVALID_PNR_DETAILS,
							HttpStatus.PRECONDITION_FAILED, Severity.INFO));
			Integer status = ticket.getStatus();
			switch (status) {
			case 0:
				response.setStatus(AppConstants.TICKET_CONFORMED);
				break;
			case 1:
				response.setStatus(AppConstants.TICKET_CANCELLED);
				break;
			case 2:
				response.setStatus(AppConstants.TICKET_NOT_CONFORMED);
				break;
			default:
				response.setStatus(AppConstants.TICKET_IN_INVALIED_STATE);
					break;
			}
			return response;
		} catch (Exception e) {
			throw new ReservationException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.INFO);
		}

	}

	@Override
	public TicketDto getTicketDetails(String mobileNumber) {
		try {
			Ticket ticket = ticketRepository.findByPassengerContactNumber(mobileNumber)
					.orElseThrow(() -> new ReservationException(AppConstants.INVALID_MOBILENUMBER,
							HttpStatus.PRECONDITION_FAILED, Severity.INFO));
			return modelMapper.map(ticket, TicketDto.class);
		} catch (Exception e) {
			throw new ReservationException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.INFO);
		}
	}

}
