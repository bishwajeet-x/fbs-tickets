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
	public TicketResponseDto cancelTicket(String pnr, String flightUrl) {
		TicketResponseDto ticket = searchTicket(pnr, flightUrl);
		FlightDto flight = getFlightData(ticket.getFlight().getFlightCode(), flightUrl);
		Date today = null;
		try {
			today = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long diff = Math.abs(today.getTime() - flight.getSta().getTime());
	    long hours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
	    
	    if(hours < 24) {
	    	ticket.getTicket().setStatus("CANCELLED");
	    	try {
	    		Ticket cancelled = ticketRepo.save(ticket.getTicket());
	    		TicketResponseDto response = new TicketResponseDto();
	    		response.setFlight(ticket.getFlight());
	    		response.setPassengers(ticket.getPassengers());
	    		response.setTicket(cancelled);
	    		return response;
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
		ticket.setNumberOfSeats(bookingReq.getNumberOfSeats());
		ticket.setFare(bookingReq.getFare());
		ticket.setFlightClass(bookingReq.getFlightClass());
		ticket.setPnr(ticket.getName().substring(0, 2).concat(flightDetails.getFlightCode().substring(0, 4).concat(String.valueOf(System.currentTimeMillis()))));
		ticket.setStatus("BOOKED");
		ticket.setUsername(bookingReq.getUsername());
		
		bookingReq.getPassengers().add(new Passenger(bookingReq.getName(), bookingReq.getGender(), bookingReq.getAge()));
		
		TicketResponseDto response = new TicketResponseDto();
		List<Passenger> passengers = new ArrayList<>();
		try {
			ticketRepo.save(ticket);
			System.out.println("Inside try after save");
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
				e.printStackTrace();
				throw new SomethingWentWrong("Could not book ticket!");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new SomethingWentWrong("Could not book ticket!");
		}
		
	}

	@Override
	public TicketResponseDto searchTicket(String pnr, String flightUrl) {
		TicketResponseDto response = new TicketResponseDto();
		Optional<Ticket> ticket = ticketRepo.findByPnr(pnr);
		if(ticket.isEmpty()) {
			throw new NotFoundException("NotFoundException: Invalid PNR!");
		}
		response.setTicket(ticket.get());
		response.setFlight(getFlightData(ticket.get().getFlightId(), flightUrl));
		response.setPassengers(fetchPassengers(ticket.get().getTicketId()));
		return response;
	}

	@Override
	public List<Passenger> fetchPassengers(long ticketId) {
		return passengerRepo.findByTicketTicketId(ticketId);
	}

	@Override
	public FlightDto getFlightData(String flightId, String flightUrl) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<FlightSchedule> flight = restTemplate.exchange(flightUrl+"search?id="+flightId, 
				HttpMethod.GET, null, 
				FlightSchedule.class);
		
		if(flight.getStatusCodeValue() != 200) {
			throw new NotFoundException("NotFoundException: Flight id "+flightId+" does not exist");
		}
		System.err.println(flight.getBody().toString());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.convertValue(flight.getBody(), FlightDto.class);
		} catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<TicketResponseDto> getTicketHistory(TicketHistoryParam param, String flightUrl) {
		Optional<List<Ticket>> tickets = ticketRepo.findByUsername(param.getUsername());
		if(tickets.isEmpty()) {
			throw new NotFoundException("NotFoundException: No tickets available with this email!");
		}
		
		List<TicketResponseDto> allTickets = new ArrayList<>();
		
		tickets.get().stream().forEach(ticket -> {
			TicketResponseDto response = new TicketResponseDto();
			response.setTicket(ticket);
			response.setFlight(getFlightData(ticket.getFlightId(), flightUrl));
			response.setPassengers(fetchPassengers(ticket.getTicketId()));
			allTickets.add(response);
		});
		
		return allTickets;
	}

}
