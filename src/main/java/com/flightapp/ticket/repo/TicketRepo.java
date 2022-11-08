package com.flightapp.ticket.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.ticket.entity.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, String> {

	Optional<Ticket> findByPnr(String pnr);

	Optional<List<Ticket>> findByUsername(String username);

}
