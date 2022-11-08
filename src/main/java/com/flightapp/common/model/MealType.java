package com.flightapp.common.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
public class MealType {
	
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
