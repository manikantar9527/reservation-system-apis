package com.persistent.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.persistent.config.AppConstants;
import com.persistent.dao.Availability;
import com.persistent.dao.TrainInfo;
import com.persistent.dto.SearchTrainDto;
import com.persistent.dto.SeatInfoDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TrainAvailabilityDto;
import com.persistent.exception.ReservationException;
import com.persistent.exception.Severity;
import com.persistent.repository.AvailabilityRepository;
import com.persistent.repository.TrainInfoRepository;
import com.persistent.service.TrainInfoService;

@Service
public class TrainInfoServiceImpl implements TrainInfoService {

	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Autowired
	private TrainInfoRepository trainInfoRepository;

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
			List<String> classTypes = Arrays.asList(AppConstants.CLASS_TYPES.split(","));
			classTypes.forEach(c -> {
				List<Availability> availableSeats = availabilityRepository
						.findByTrainTrainIdAndClassTypeOrderByCoach(t.getTrainId(), c);
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

	@Override
	public StatusDto addTrainDetails(TrainInfo trainInfo) {
		trainInfoRepository.save(trainInfo);
		return new StatusDto(1, AppConstants.TRAIN_DETAILS_ADDED);
	}

}
