package com.persistent.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.persistent.config.AppConstants;
import com.persistent.dao.Availability;
import com.persistent.dao.TrainInfo;
import com.persistent.dto.AvailabilityDto;
import com.persistent.dto.StatusDto;
import com.persistent.exception.ReservationException;
import com.persistent.exception.Severity;
import com.persistent.repository.AvailabilityRepository;
import com.persistent.repository.TrainInfoRepository;
import com.persistent.service.AvailabilityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AvailabilityServiceImpl implements AvailabilityService {

	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Autowired
	private TrainInfoRepository trainInfoRepository;

	private void addAvailabilities(AvailabilityDto reqDto) { 
		TrainInfo trainInfo = trainInfoRepository.findByTrainId(reqDto.getTrainId());
		int seatsPerCoach3A = trainInfo.getTotal3ASeats() / trainInfo.getNoOf3ACoaches();
		for (int i = 1; i < trainInfo.getNoOf3ACoaches() + 1; i++) {
			availabilityRepository.save(new Availability(null, reqDto.getDate(), trainInfo, new Date(),
					seatsPerCoach3A / 2, seatsPerCoach3A / 2, "c" + i, "3A", 0, 0));
		} 

		int seatsPerCoach2A = trainInfo.getTotal2ASeats() / trainInfo.getNoOf2ACoaches();
		for (int i = 1; i < trainInfo.getNoOf2ACoaches() + 1; i++) {
			availabilityRepository.save(new Availability(null, reqDto.getDate(), trainInfo, new Date(),
					seatsPerCoach2A / 2, seatsPerCoach2A / 2, "c" + i, "2A", 0, 0));
		}
		int seatsPerCoachSL = trainInfo.getTotalSLSeats() / trainInfo.getNoOfSLCoaches();

		for (int i = 1; i < trainInfo.getNoOfSLCoaches() + 1; i++) {
			availabilityRepository.save(new Availability(null, reqDto.getDate(), trainInfo, new Date(),
					seatsPerCoachSL / 2, seatsPerCoachSL / 2, "c" + i, "SL", 0, 0));
		}
		int seatsPerCoach2S = trainInfo.getTotal2SSeats() / trainInfo.getNoOf2SCoaches();
		for (int i = 1; i < trainInfo.getNoOf2SCoaches() + 1; i++) {
			availabilityRepository.save(new Availability(null, reqDto.getDate(), trainInfo, new Date(),
					seatsPerCoach2S / 2, seatsPerCoach2S / 2, "c" + i, "2S", 0, 0));
		}
	}

	@Override
	public StatusDto addAvailability(AvailabilityDto reqDto) {
		try {
			log.info("addAvailability request details" + reqDto);
			List<Availability> availabilities = availabilityRepository.findByTrainTrainIdAndDate(reqDto.getTrainId(),
					reqDto.getDate()); 

			if (availabilities.isEmpty()) {
				addAvailabilities(reqDto);
				log.info(AppConstants.AVAILABILITY_DETAILS_ADDED);
				return new StatusDto(0, AppConstants.AVAILABILITY_DETAILS_ADDED);
			}
			log.info(AppConstants.AVAILABILITY_DETAILS_ALREADY_EXIST);
			return new StatusDto(1, AppConstants.AVAILABILITY_DETAILS_ALREADY_EXIST);
		} catch (Exception e) {
			throw new ReservationException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.INFO);
		} 
	}

	@Override
	public List<Availability> ticketAvailability(AvailabilityDto reqDto) {
		List<Availability> list = availabilityRepository.findByTrainTrainIdAndDateAndClassType(reqDto.getTrainId(),
				reqDto.getDate(),reqDto.getClassType());
		if (list.isEmpty())
			throw new ReservationException(AppConstants.INVALID_DETAILS, HttpStatus.BAD_REQUEST, Severity.INFO);
		else
			return list;
	}
}
