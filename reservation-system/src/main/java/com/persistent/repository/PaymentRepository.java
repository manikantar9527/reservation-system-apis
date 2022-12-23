package com.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistent.dao.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
