package com.persistent.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.persistent.config.AppConstants;
import com.persistent.exception.ReservationException;
import com.persistent.exception.Severity;

public class Test {

	public static void main(String[] args) throws ParseException {Calendar cal1 = Calendar.getInstance();
	Calendar cal2 = Calendar.getInstance();
	cal1.setTime(new Date());
	//cal2.setTime(ticket.getDate());

	if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
			&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
		throw new ReservationException(AppConstants.TICKET_CANCELLATION_NOT_ALLOWED, HttpStatus.PRECONDITION_FAILED,
				Severity.INFO);}

}
