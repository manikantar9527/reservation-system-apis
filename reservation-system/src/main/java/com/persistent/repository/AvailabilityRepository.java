package com.persistent.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistent.dao.Availability;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

	List<Availability> findByTrainTrainIdAndDate(Long trainId, Date date);

	List<Availability> findByTrainTrainId(Long trainId);

	Availability findByTrainTrainIdAndDateAndCoach(Long trainId, Date date, String coach);

	List<Availability> findByTrainTrainIdAndClassType(Long trainId, String c);

	List<Availability> findByTrainTrainIdAndDateAndClassType(Long trainId, Date date, String classType);

	List<Availability> findByTrainTrainIdAndDateAndClassTypeOrderByCoach(Long trainId, Date date, String classType);

	Availability findByTrainTrainIdAndDateAndClassTypeAndCoach(Long trainId, Date date, String classType, String coach);

}
