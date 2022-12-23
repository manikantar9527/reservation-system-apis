package com.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistent.dao.RailwayStaff;

@Repository
public interface RailwayStaffRepository extends JpaRepository<RailwayStaff, Long> {

}
