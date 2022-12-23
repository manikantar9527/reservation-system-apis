package com.persistent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistent.dao.TrainInfo;

@Repository
public interface TrainInfoRepository extends JpaRepository<TrainInfo, Long>{


	TrainInfo findByTrainId(Long trainId);

	List<TrainInfo> findBySourceAndDestinationAndAvailableDaysContaining(String source, String destination,
			String valueOf);


}
