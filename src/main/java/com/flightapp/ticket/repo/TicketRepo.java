package com.flightapp.ticket.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.ticket.entity.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Long> {

	Optional<Ticket> findByPnr(String pnr);

	Optional<List<Ticket>> findByEmail(String email);

}
