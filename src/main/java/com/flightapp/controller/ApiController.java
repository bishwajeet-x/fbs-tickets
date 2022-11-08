package com.flightapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.ticket.dto.FlightDto;
import com.flightapp.ticket.dto.TicketDto;
import com.flightapp.ticket.dto.TicketHistoryParam;
import com.flightapp.ticket.dto.TicketResponseDto;
import com.flightapp.ticket.entity.Passenger;
import com.flightapp.ticket.entity.Ticket;
import com.flightapp.ticket.service.TicketService;

@RestController
@RequestMapping("/api/ticket")
public class ApiController {

	@Value("${flighturl}") String flightUrl;
	@Autowired private TicketService ticketService;
	
	@PostMapping("/")
	public TicketResponseDto bookTicket(@RequestBody TicketDto bookingReq) {
		System.out.println("Inside bookTicket");
		return ticketService.bookTicket(bookingReq, getFlightData(bookingReq.getFlightId()));
	}

	@GetMapping("/search")
	public TicketResponseDto searchTicket(@RequestParam("pnr") String pnr) {
		return ticketService.searchTicket(pnr, flightUrl);
	}
	
	@PostMapping("/history")
	public List<TicketResponseDto> getTicketHistory(@RequestBody TicketHistoryParam param) {
		return ticketService.getTicketHistory(param, flightUrl);
	}
	
	@GetMapping("/cancel")
	public TicketResponseDto cancelTicket(@RequestParam("pnr") String pnr) {
		return ticketService.cancelTicket(pnr, flightUrl);
	}
	
	public List<Passenger> fetchPassengers(long ticketId) {
		return ticketService.fetchPassengers(ticketId);
	}
	
	public FlightDto getFlightData(String flightId) {
		return ticketService.getFlightData(flightId, flightUrl);
	}
	
}