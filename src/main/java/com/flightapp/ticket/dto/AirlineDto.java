package com.flightapp.ticket.dto;

import java.util.ArrayList;
import java.util.Collection;

public class AirlineDto {
	
	private long airlineId;
	private String airlineName;
	private int noOfSeats;
	private boolean mealAvailable;
	private Collection<MealType> mealType = new ArrayList<>();
	private AirlineStatus airlineStatus;
	public AirlineDto() {}

	public AirlineDto(String airlineName, int noOfSeats, boolean mealAvailable,
			Collection<MealType> mealType, AirlineStatus airlineStatus) {
		this.airlineName = airlineName;
		this.noOfSeats = noOfSeats;
		this.mealAvailable = mealAvailable;
		this.mealType = mealType;
		this.airlineStatus = airlineStatus;
	}
	

	public long getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(long airlineId) {
		this.airlineId = airlineId;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public int getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	public boolean isMealAvailable() {
		return mealAvailable;
	}

	public void setMealAvailable(boolean mealAvailable) {
		this.mealAvailable = mealAvailable;
	}

	public Collection<MealType> getMealType() {
		return mealType;
	}

	public void setMealType(Collection<MealType> mealType) {
		this.mealType = mealType;
	}

	public AirlineStatus getAirlineStatus() {
		return airlineStatus;
	}

	public void setAirlineStatus(AirlineStatus airlineStatus) {
		this.airlineStatus = airlineStatus;
	}

	@Override
	public String toString() {
		return "Airline [airlineId=" + airlineId + ", airlineName=" + airlineName + ", noOfSeats=" + noOfSeats
				+ ", mealAvailable=" + mealAvailable + ", mealType=" + mealType + ", airlineStatus=" + airlineStatus
				+ "]";
	}
}

class AirlineStatus {
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
		return "AirlineStatus [id=" + id + ", status=" + status + "]";
	}
}

class MealType {
	
	private int typeId;
	private String typeName;
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public MealType() {}
	
	public MealType(String typeName) {
		this.typeName = typeName;
	}
	
	@Override
	public String toString() {
		return "MealType [typeId=" + typeId + ", typeName=" + typeName + "]";
	}
	
}

