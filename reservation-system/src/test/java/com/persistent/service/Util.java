package com.persistent.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.persistent.dao.Availability;
import com.persistent.dao.TrainInfo;

public class Util {
	public static List<Availability> getAvailabilities(TrainInfo train, Date date) {
		Availability a1 = Availability.builder().availabilityId(1L).classType("3A").coach("c1").date(date)
				.LowerWaitingList(0).upperWaitingList(0).noOfLowerSeatsAvailable(10).noOfUpperSeatsAvailable(10)
				.noOfUpperSeatsAvailable(10).train(train).build();
		Availability a2 = Availability.builder().availabilityId(1L).classType("2A").coach("c1").date(date)
				.LowerWaitingList(0).upperWaitingList(0).noOfLowerSeatsAvailable(10).noOfUpperSeatsAvailable(10)
				.noOfUpperSeatsAvailable(10).train(train).build();
		Availability a3 = Availability.builder().availabilityId(1L).classType("2S").coach("c1").date(date)
				.LowerWaitingList(0).upperWaitingList(0).noOfLowerSeatsAvailable(10).noOfUpperSeatsAvailable(10)
				.noOfUpperSeatsAvailable(10).train(train).build();
		Availability a4 = Availability.builder().availabilityId(1L).classType("SL").coach("c1").date(date)
				.LowerWaitingList(0).upperWaitingList(0).noOfLowerSeatsAvailable(10).noOfUpperSeatsAvailable(10)
				.noOfUpperSeatsAvailable(10).train(train).build();
		List<Availability> availabilities = Arrays.asList(a1, a2, a3, a4);
		return availabilities;
	}
	
	public static List<Availability> getAvailabilitiesWithWeightingList(TrainInfo train,Date date) {
		Availability a1 = Availability.builder().availabilityId(1L).classType("3A").coach("c1").date(date)
				.LowerWaitingList(5).upperWaitingList(5).noOfLowerSeatsAvailable(0).noOfUpperSeatsAvailable(0)
				.noOfUpperSeatsAvailable(0).train(train).build();
		Availability a2 = Availability.builder().availabilityId(1L).classType("2A").coach("c1").date(date)
				.LowerWaitingList(5).upperWaitingList(5).noOfLowerSeatsAvailable(0).noOfUpperSeatsAvailable(0)
				.noOfUpperSeatsAvailable(0).train(train).build();
		Availability a3 = Availability.builder().availabilityId(1L).classType("2S").coach("c1").date(date)
				.LowerWaitingList(5).upperWaitingList(5).noOfLowerSeatsAvailable(0).noOfUpperSeatsAvailable(0)
				.noOfUpperSeatsAvailable(0).train(train).build();
		Availability a4 = Availability.builder().availabilityId(1L).classType("SL").coach("c1").date(date)
				.LowerWaitingList(5).upperWaitingList(5).noOfLowerSeatsAvailable(0).noOfUpperSeatsAvailable(0)
				.noOfUpperSeatsAvailable(0).train(train).build();
		List<Availability> availabilities = Arrays.asList(a1, a2, a3, a4);
		return availabilities;
	}
}
