package com.persistent.serviceimpl;

import java.util.Comparator;
import java.util.List;
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
import com.persistent.exception.ReservationException;
import com.persistent.exception.Severity;
import com.persistent.model.AppConstants;
import com.persistent.repository.AvailabilityRepository;
import com.persistent.repository.PassengerRepository;
import com.persistent.repository.TicketRepository;
import com.persistent.repository.TrainInfoRepository;
import com.persistent.service.ReservationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegistrationServiceImpl implements ReservationService {

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

	@Override
	public Ticket bookTicket(BookTicketDto reqDto) {
		try {
			log.info("input request details" + reqDto);
			Passenger passenger = passengerRepository.findByUserId(reqDto.getUserId());
			if(passenger==null)
				throw new ReservationException(AppConstants.INVALID_USER_ID, HttpStatus.BAD_REQUEST,
						Severity.INFO);
			
			AvailabilityDto availabilityDto = new AvailabilityDto();
			availabilityDto.setDate(reqDto.getDate());
			// availabilityDto.setMobileNumber(reqDto.getPassenger().getMobileNumber());
			availabilityDto.setTrainId(reqDto.getTrainId());
			ResponseEntity<List<Availability>> res = service.ticketAvailability(availabilityDto);
			List<Availability> availabilities = res.getBody();
			String seatNumber = null;
			String coach = null;

			TrainInfo trainInfo = trainInfoRepository.findByTrainId(reqDto.getTrainId());
			if(trainInfo==null)
				throw new ReservationException(AppConstants.INVALID_TRAIN_ID, HttpStatus.BAD_REQUEST,
						Severity.INFO);
			Ticket ticket = new Ticket();
			if (!availabilities.isEmpty()) {
				if (availabilities.stream().collect(Collectors.summingInt(Availability::getNoOfLowerSeatsAvailable))
						+ availabilities.stream()
								.collect(Collectors.summingInt(Availability::getNoOfUpperSeatsAvailable)) == 0) {
					throw new ReservationException(AppConstants.SEATS_NOT_AVAILABLE, HttpStatus.ACCEPTED,
							Severity.INFO);
				} else if (AppConstants.LOWER.equalsIgnoreCase(reqDto.getBerthType())
						|| (passenger.getGender().equalsIgnoreCase(AppConstants.FEMALE) && passenger.getAge() > 40)
						|| passenger.getAge() < 15 || passenger.getAge() > 60) {
					if (availabilities.stream()
							.collect(Collectors.summingInt(Availability::getNoOfLowerSeatsAvailable)) == 0)
						throw new ReservationException(AppConstants.SEATS_NOT_AVAILABLE_IN_LOWER, HttpStatus.ACCEPTED,
								Severity.INFO);
					else {
						Availability availability = availabilities.stream().collect(
								Collectors.maxBy(Comparator.comparing(Availability::getNoOfLowerSeatsAvailable))).get();

						availability.setNoOfLowerSeatsAvailable(availability.getNoOfLowerSeatsAvailable() - 1);
						availabilityRepository.save(availability);
						seatNumber = availability.getCoach() + "-" + (availability.getNoOfLowerSeatsAvailable() + 1);
						coach = availability.getCoach();
						ticket.setBerthType(AppConstants.LOWER);
					}
				} else {
					if (availabilities.stream()
							.collect(Collectors.summingInt(Availability::getNoOfUpperSeatsAvailable)) != 0) {
						Availability availability = availabilities.stream().collect(
								Collectors.maxBy(Comparator.comparing(Availability::getNoOfUpperSeatsAvailable))).get();
						availability.setNoOfUpperSeatsAvailable(availability.getNoOfUpperSeatsAvailable() - 1);
						availabilityRepository.save(availability);
						seatNumber = availability.getCoach() + "-" + (availability.getNoOfUpperSeatsAvailable() + 1);
						coach = availability.getCoach();
						ticket.setBerthType(AppConstants.UPPER);
					} else {
						Availability availability = availabilities.stream().collect(
								Collectors.maxBy(Comparator.comparing(Availability::getNoOfLowerSeatsAvailable))).get();
						availability.setNoOfLowerSeatsAvailable(availability.getNoOfLowerSeatsAvailable() - 1);
						ticket.setBerthType(AppConstants.LOWER);
						seatNumber = availability.getCoach() + "-" + (availability.getNoOfLowerSeatsAvailable() + 1);
						coach = availability.getCoach();
						availabilityRepository.save(availability);
					}

				}
			}
			ticket.setTicketCost(reqDto.getTicketCost());
			ticket.setStartingLocation(reqDto.getStartingLocation());
			ticket.setDestination(reqDto.getDestination());
			ticket.setDate(reqDto.getDate());
			ticket.setStatus(0);
			ticket.setCoach(coach);
			ticket.setSeatNumber(seatNumber);
			ticket.setTrain(trainInfo);
			ticket.setPassenger(passenger);
			ticketRepository.save(ticket);
			return ticket;
		} catch (Exception e) {
			throw new ReservationException(e.getMessage(), HttpStatus.ACCEPTED, Severity.INFO);
		}
	}

}
