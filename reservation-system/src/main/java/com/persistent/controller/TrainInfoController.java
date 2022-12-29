package com.persistent.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.dao.TrainInfo;
import com.persistent.dto.SearchTrainDto;
import com.persistent.dto.StatusDto;
import com.persistent.dto.TrainAvailabilityDto;
import com.persistent.service.TrainInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("rest")
public class TrainInfoController {

	@Autowired
	private TrainInfoService service;

	@PostMapping("searchTrain")
	public ResponseEntity<List<TrainAvailabilityDto>> searchTrain(@Valid @RequestBody SearchTrainDto reqDto) {
		log.info("searchTrain() excecution - started");
		return ResponseEntity.ok(service.searchTrain(reqDto));
	}

	@PostMapping("add/train")
	public ResponseEntity<StatusDto> addTrainDetails(@Valid @RequestBody TrainInfo train) {
		log.info("addTrainDetails() excecution - started");
		return ResponseEntity.ok(service.addTrainDetails(train));
	}

}
