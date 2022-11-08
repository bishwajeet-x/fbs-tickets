package com.flightapp.ticket.dto;

import java.util.Collection;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class FlightDto {
	
	private String flightCode;
	private AirlineDto airline;
	
	private Date sta;
	private Date eta;
	private int flightHours;
	
	private String source;
	private String destination;
	
	private Collection<FlightClass> flightClass;
	
	private FlightStatus status;

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public AirlineDto getAirline() {
		return airline;
	}

	public void setAirline(AirlineDto airline) {
		this.airline = airline;
	}

	public Date getSta() {
		return sta;
	}

	public void setSta(Date sta) {
		this.sta = sta;
	}

	public Date getEta() {
		return eta;
	}

	public void setEta(Date eta) {
		this.eta = eta;
	}

	public int getFlightHours() {
		return flightHours;
	}

	public void setFlightHours(int flightHours) {
		this.flightHours = flightHours;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Collection<FlightClass> getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(Collection<FlightClass> flightClass) {
		this.flightClass = flightClass;
	}

	public FlightStatus getStatus() {
		return status;
	}

	public void setStatus(FlightStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FlightSchedule [flightCode=" + flightCode + ", airline=" + airline + ", sta=" + sta + ", eta=" + eta
				+ ", flightHours=" + flightHours + ", source=" + source + ", destination=" + destination
				+ ", flightClass=" + flightClass + ", status=" + status + "]";
	}
}

class FlightStatus {
	private int id;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "FlightStatus [id=" + id + ", status=" + status + "]";
	}
	
}

class FlightClass {
	private long classId;
	
	private String name;
	private double fare;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	
	@Override
	public String toString() {
		return "FlightClass [name=" + name + ", fare=" + fare + "]";
	}
}
