package com.onclick.chicagodata.model;

public class ChicagoCrime {

	private long id;
	private String crimeid;
	private String block;
	private String crimetype;
	private String longitude;
	private String latitude;
	private String date;
	
	public ChicagoCrime() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCrimeid() {
		return crimeid;
	}

	public void setCrimeid(String crimeid) {
		this.crimeid = crimeid;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getCrimetype() {
		return crimetype;
	}

	public void setCrimetype(String crimetype) {
		this.crimetype = crimetype;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
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
