package com.flightapp.ticket.dto;

public class TicketHistoryParam {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "TicketHistoryParam [email=" + email + "]";
	}
}
