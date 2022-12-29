package com.persistent.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainAvailabilityDto {
	private String trainName;
	private String trainNumber;
	private Long trainId;
	private List<SeatInfoDto> SeatInfoList;
}
