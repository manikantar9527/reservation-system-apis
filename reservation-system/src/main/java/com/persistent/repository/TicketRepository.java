package com.persistent.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistent.dao.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	Ticket findByPassengerUserIdAndTicketIdAndStatus(Long userId, Long ticketId, int i);

	Optional<Ticket> findByPassengerContactNumberAndTicketIdAndStatus(String contactNumber, Long ticketId, int i);

	//Ticket findByTrainTrainIdAndDateAndClassType(Long trainId, Date date, String classType);
	

	Ticket findFirstByTrainTrainIdAndDateAndClassTypeOrderByTicketId(Long trainId, Date date, String classType);

	Ticket findFirstByTrainTrainIdAndDateAndClassTypeAndStatusOrderByTicketId(Long trainId, Date date, String classType,
			int i);

	Ticket findFirstByTrainTrainIdAndDateAndClassTypeAndStatusAndBerthTypeOrderByTicketId(Long trainId, Date date,
			String classType, int i, String berthType);

	Optional<Ticket> findByPnr(String pnr);

	Optional<Ticket> findByPassengerContactNumber(String mobileNumber);

}
