package com.flightapp.ticket.dto;

import java.util.List;

import com.flightapp.ticket.entity.Passenger;
import com.flightapp.ticket.entity.Ticket;

public class TicketResponseDto {

	private Ticket ticket;
	private FlightDto flight;
	private List<Passenger> passengers;
	
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public FlightDto getFlight() {
		return flight;
	}
	public void setFlight(FlightDto flight) {
		this.flight = flight;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	@Override
	public String toString() {
		return "TicketResponseDto [ticket=" + ticket + ", flight=" + flight + ", passengers=" + passengers + "]";
	}
}
