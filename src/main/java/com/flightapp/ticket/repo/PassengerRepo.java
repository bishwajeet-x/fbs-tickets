package com.flightapp.ticket.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.ticket.entity.Passenger;

public interface PassengerRepo extends JpaRepository<Passenger, Long> {

	List<Passenger> findByTicketTicketId(long ticketId);

}
