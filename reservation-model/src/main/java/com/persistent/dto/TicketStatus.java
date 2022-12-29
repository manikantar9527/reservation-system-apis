package com.persistent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TicketStatus {
	CONFORMED("Conformed", 0),CANCELLED("Cancelled", 1), WAITINGLIST("WaitingList", 2);

	private final String key;
	private final Integer value;

}
