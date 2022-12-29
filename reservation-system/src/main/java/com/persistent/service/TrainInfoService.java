package com.persistent.service;

import java.util.List;

import com.persistent.dao.TrainInfo;
import com.persistent.dto.SearchTrainDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TrainAvailabilityDto;

public interface TrainInfoService {
	List<TrainAvailabilityDto> searchTrain(SearchTrainDto reqDto);

	StatusDto addTrainDetails(TrainInfo train);

}
