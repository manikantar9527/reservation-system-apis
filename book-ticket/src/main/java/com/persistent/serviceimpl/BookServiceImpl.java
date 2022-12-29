package com.persistent.serviceimpl;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.persistent.client.ReservationSystemClient;
import com.persistent.dao.Availability;
import com.persistent.dao.Passenger;
import com.persistent.dao.Ticket;
import com.persistent.dao.TrainInfo;
import com.persistent.dto.AvailabilityDto;
import com.persistent.dto.BookTicketDto;
import com.persistent.dto.TicketStatus;
import com.persistent.exception.ReservationException;
import com.persistent.exception.Severity;
import com.persistent.model.AppConstants;
import com.persistent.repository.AvailabilityRepository;
import com.persistent.repository.PassengerRepository;
import com.persistent.repository.TicketRepository;
import com.persistent.repository.TrainInfoRepository;
import com.persistent.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

	@Autowired
	private ReservationSystemClient service;
	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private TrainInfoRepository trainInfoRepository;

	private List<Availability> callReservationService(BookTicketDto reqDto) {
		AvailabilityDto availabilityDto = new AvailabilityDto();
		availabilityDto.setDate(reqDto.getDate());
		availabilityDto.setClassType(reqDto.getClassType());

		availabilityDto.setTrainId(reqDto.getTrainId());
		ResponseEntity<List<Availability>> res = service.ticketAvailability(availabilityDto);// get only 3A
		return res.getBody();
	}

	@Override
	public Ticket bookTicket(BookTicketDto reqDto) {
		try {
			log.info("input request details" + reqDto);
			Passenger passenger = passengerRepository.findByUserId(reqDto.getUserId())
					.orElseThrow(() -> new ReservationException(AppConstants.INVALID_USER_ID,
							HttpStatus.PRECONDITION_FAILED, Severity.INFO));
			List<Availability> availabilities = callReservationService(reqDto);
			String seatNumber = null;
			String coach = null;
			TrainInfo trainInfo = trainInfoRepository.findByTrainId(reqDto.getTrainId())
					.orElseThrow(() -> new ReservationException(AppConstants.INVALID_TRAIN_ID,
							HttpStatus.PRECONDITION_FAILED, Severity.INFO));
			Ticket ticket = new Ticket();
			ticket.setClassType(reqDto.getClassType());
			ticket.setStatus(TicketStatus.CONFORMED.getValue());
			ticket.setPnr(String.format("%06d", new Random().nextInt(999999)));
			int totalSeatsAvailable = availabilities.stream()
					.collect(Collectors.summingInt(Availability::getNoOfLowerSeatsAvailable))
					+ availabilities.stream().collect(Collectors.summingInt(Availability::getNoOfUpperSeatsAvailable));
			Availability avail = availabilities.get(0);
			if (!availabilities.isEmpty()) {
				if ((totalSeatsAvailable == 0) && avail.getLowerWaitingList() == 50
						&& avail.getUpperWaitingList() == 50) {
					throw new ReservationException(AppConstants.SEATS_NOT_AVAILABLE, HttpStatus.ACCEPTED,
							Severity.INFO);
				} else if (AppConstants.LOWER.equalsIgnoreCase(reqDto.getBerthType())
						|| (passenger.getGender().equalsIgnoreCase(AppConstants.FEMALE) && passenger.getAge() > 40)
						|| passenger.getAge() < 15 || passenger.getAge() > 60) {
					if (availabilities.stream()
							.collect(Collectors.summingInt(Availability::getNoOfLowerSeatsAvailable)) == 0
							&& avail.getLowerWaitingList() == 50)
						throw new ReservationException(AppConstants.SEATS_NOT_AVAILABLE_IN_LOWER, HttpStatus.ACCEPTED,
								Severity.INFO);
					else {
						Availability availability = availabilities.stream().collect(
								Collectors.maxBy(Comparator.comparing(Availability::getNoOfLowerSeatsAvailable))).get();
						if (totalSeatsAvailable != 0) {
							availability.setNoOfLowerSeatsAvailable(availability.getNoOfLowerSeatsAvailable() - 1);
							seatNumber = reqDto.getClassType() + "-" + availability.getCoach() + "-"
									+ (availability.getNoOfLowerSeatsAvailable() + 1);
							coach = availability.getCoach();
						} else {
							avail.setLowerWaitingList(avail.getLowerWaitingList() + 1);
							ticket.setStatus(TicketStatus.WAITINGLIST.getValue());
							availabilityRepository.save(avail);
						}

						availabilityRepository.save(availability);

						ticket.setBerthType(AppConstants.LOWER);
					}
				} else {
					if (availabilities.stream()
							.collect(Collectors.summingInt(Availability::getNoOfUpperSeatsAvailable)) != 0
							|| avail.getUpperWaitingList() != 50) {
						Availability availability = availabilities.stream().collect(
								Collectors.maxBy(Comparator.comparing(Availability::getNoOfUpperSeatsAvailable))).get();
						if (totalSeatsAvailable != 0) {
							availability.setNoOfUpperSeatsAvailable(availability.getNoOfUpperSeatsAvailable() - 1);
							seatNumber = reqDto.getClassType() + "-" + availability.getCoach() + "-"
									+ (availability.getNoOfUpperSeatsAvailable() + 1);
							coach = availability.getCoach();
						} else {
							avail.setUpperWaitingList(avail.getUpperWaitingList() + 1);
							ticket.setStatus(TicketStatus.WAITINGLIST.getValue());
							availabilityRepository.save(avail);
						}

						availabilityRepository.save(availability);
						ticket.setBerthType(AppConstants.UPPER);
					} else {
						Availability availability = availabilities.stream().collect(
								Collectors.maxBy(Comparator.comparing(Availability::getNoOfLowerSeatsAvailable))).get();

						ticket.setBerthType(AppConstants.LOWER);
						if (totalSeatsAvailable != 0) {
							availability.setNoOfLowerSeatsAvailable(availability.getNoOfLowerSeatsAvailable() - 1);
							seatNumber = reqDto.getClassType() + "-" + availability.getCoach() + "-"
									+ (availability.getNoOfLowerSeatsAvailable() + 1);
							coach = availability.getCoach();
						} else {
							avail.setLowerWaitingList(avail.getLowerWaitingList() + 1);
							ticket.setStatus(TicketStatus.WAITINGLIST.getValue());
							availabilityRepository.save(avail);
						}
						availabilityRepository.save(avail);
						availabilityRepository.save(availability);
					}
				}
			}
			return addTicketDetails(reqDto, ticket, seatNumber, coach, trainInfo, passenger);
		} catch (Exception e) {
			throw new ReservationException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.INFO);
		}
	}

	private Ticket addTicketDetails(BookTicketDto reqDto, Ticket ticket, String seatNumber, String coach,
			TrainInfo trainInfo, Passenger passenger) {
		ticket.setTicketCost(reqDto.getTicketCost());
		ticket.setStartingLocation(reqDto.getStartingLocation());
		ticket.setDestination(reqDto.getDestination());
		ticket.setDate(reqDto.getDate());

		ticket.setCoach(coach);
		ticket.setSeatNumber(seatNumber);
		ticket.setTrain(trainInfo);
		ticket.setPassenger(passenger);
		ticket = ticketRepository.save(ticket);
		return ticket;
	}

}
