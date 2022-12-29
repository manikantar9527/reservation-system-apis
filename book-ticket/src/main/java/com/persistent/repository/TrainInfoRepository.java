package com.persistent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistent.dao.TrainInfo;

@Repository
public interface TrainInfoRepository extends JpaRepository<TrainInfo, Long>{


	Optional<TrainInfo> findByTrainId(Long trainId);

	List<TrainInfo> findBySourceAndDestinationAndAvailableDaysContaining(String source, String destination,
			String valueOf);


}
