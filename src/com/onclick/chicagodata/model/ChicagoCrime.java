package com.onclick.chicagodata.model;

public class ChicagoCrime {

	private long id;
	private String crimetype;
	private double longitude;
	private double latitude;
	private String date;
	
	public ChicagoCrime() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCrimetype() {
		return crimetype;
	}

	public void setCrimetype(String crimetype) {
		this.crimetype = crimetype;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		
		return crimetype;
	}
	
	

	
}
