package com.apps.hamza.missingapp;


public class Place {

	private Double longitude ;
	private Double latitude ; 
	private String address ;
	private String timeStamp;
	
	
	
	public Place() {
	
	}
	public Place(Double longitude, Double latitude, String address,
			String timeStamp) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
		this.timeStamp = timeStamp;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	} 
	
}
