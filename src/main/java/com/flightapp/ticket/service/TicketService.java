package com.flightapp.ticket.service;

import java.util.List;

import com.flightapp.ticket.dto.FlightDto;
import com.flightapp.ticket.dto.TicketDto;
import com.flightapp.ticket.dto.TicketHistoryParam;
import com.flightapp.ticket.dto.TicketResponseDto;
import com.flightapp.ticket.entity.Passenger;
import com.flightapp.ticket.entity.Ticket;

public interface TicketService {

	public TicketResponseDto cancelTicket(String pnr, String flightUrl);
	public TicketResponseDto bookTicket(TicketDto bookingReq, FlightDto flightDetails);
	public TicketResponseDto searchTicket(String pnr, String flightUrl);
	public List<Passenger> fetchPassengers(long flightId);
	public FlightDto getFlightData(String flightId, String flightUrl);
	public List<TicketResponseDto> getTicketHistory(TicketHistoryParam param, String flightUrl);
}
