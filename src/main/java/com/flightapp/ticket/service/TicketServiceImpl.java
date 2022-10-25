package com.flightapp.ticket.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightapp.common.exception.GenericException;
import com.flightapp.common.exception.NotFoundException;
import com.flightapp.common.exception.SomethingWentWrong;
import com.flightapp.common.model.DefaultResponse;
import com.flightapp.common.model.FlightSchedule;
import com.flightapp.ticket.dto.FlightDto;
import com.flightapp.ticket.dto.TicketDto;
import com.flightapp.ticket.dto.TicketHistoryParam;
import com.flightapp.ticket.dto.TicketResponseDto;
import com.flightapp.ticket.entity.Passenger;
import com.flightapp.ticket.entity.Ticket;
import com.flightapp.ticket.repo.PassengerRepo;
import com.flightapp.ticket.repo.TicketRepo;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Autowired private TicketRepo ticketRepo;
	@Autowired private PassengerRepo passengerRepo;
	
	SimpleDateFormat sdf = new SimpleDateFormat();

	@Override
	public Ticket cancelTicket(String pnr) {
		Ticket ticket = searchTicket(pnr);
		FlightDto flight = getFlightData(ticket.getFlightId());
		Date today = null;
		try {
			today = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long diff = Math.abs(today.getTime() - flight.getScheduledFor().getTime());
	    long hours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
	    
	    if(hours > 24) {
	    	ticket.setStatus("CANCELLED");
	    	try {
	    		return ticketRepo.save(ticket);
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    		throw new SomethingWentWrong("Failed to cancel ticket!");
	    	}
	    } else {
	    	throw new GenericException("Tickets cannot be cancelled before 24 hours!");
	    }
	    
	}

	@Override
	public TicketResponseDto bookTicket(TicketDto bookingReq, FlightDto flightDetails) {
		Ticket ticket = new Ticket();
		ticket.setName(bookingReq.getName());
		ticket.setEmail(bookingReq.getEmail());
		ticket.setMeal(bookingReq.getMeal());
		ticket.setFlightId(bookingReq.getFlightId());
		ticket.setNumberOfSeats(bookingReq.getPassengers().size());
		ticket.setPnr(ticket.getName().substring(0, 2).concat(flightDetails.getAirline().getAirlineName().concat(String.valueOf(System.currentTimeMillis()))));
		ticket.setStatus("BOOKED");
		
		TicketResponseDto response = new TicketResponseDto();
		List<Passenger> passengers = new ArrayList<>();
		try {
			ticketRepo.save(ticket);
			
			try {
				bookingReq.getPassengers().forEach(p -> {
					Passenger passenger = new Passenger();
					passenger.setTicket(ticket);
					passenger.setName(p.getName());
					passenger.setAge(p.getAge());
					passenger.setGender(p.getGender());
					passengers.add(passenger);
					passengerRepo.save(passenger);
					
				});
				response.setTicket(ticket);
				response.setFlight(flightDetails);
				response.setPassengers(passengers);
				return response;
			} catch (Exception e) {
				throw new SomethingWentWrong("Could not book ticket!");
			}
			
		} catch(Exception e) {
			throw new SomethingWentWrong("Could not book ticket!");
		}
		
	}

	@Override
	public Ticket searchTicket(String pnr) {
		Optional<Ticket> ticket = ticketRepo.findByPnr(pnr);
		if(ticket.isEmpty()) {
			throw new NotFoundException("NotFoundException: Invalid PNR!");
		}
		return ticket.get();
	}

	@Override
	public List<Passenger> fetchPassengers(long ticketId) {
		return passengerRepo.findByTicketTicketId(ticketId);
	}

	@Override
	public FlightDto getFlightData(long flightId) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<FlightSchedule> flight = restTemplate.exchange("http://localhost:8080/api/flights/search?id="+flightId, 
				HttpMethod.GET, null, 
				FlightSchedule.class);
		
		if(flight.getStatusCodeValue() != 200) {
			throw new NotFoundException("NotFoundException: Flight id "+flightId+" does not exist");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.convertValue(flight.getBody(), FlightDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<TicketResponseDto> getTicketHistory(TicketHistoryParam param) {
		Optional<List<Ticket>> tickets = ticketRepo.findByEmail(param.getEmail());
		if(tickets.isEmpty()) {
			throw new NotFoundException("NotFoundException: No tickets available with this email!");
		}
		
		List<TicketResponseDto> allTickets = new ArrayList<>();
		
		tickets.get().stream().forEach(ticket -> {
			TicketResponseDto response = new TicketResponseDto();
			response.setTicket(ticket);
			response.setFlight(getFlightData(ticket.getFlightId()));
			response.setPassengers(fetchPassengers(ticket.getTicketId()));
			allTickets.add(response);
		});
		
		return allTickets;
	}

}
