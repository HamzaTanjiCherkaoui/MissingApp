package com.apps.hamza.missingapp;


import java.util.ArrayList;

public class MissingPerson {

	private String firstName ;
	private String lastName ;
	private ArrayList<Photo> photos ;
	private ArrayList<Place> places ; 
	private boolean status ;
	private User user ;

	
	
	public MissingPerson() {
		
	}

	public MissingPerson(String firstName, String lastName,
			ArrayList<Photo> photos, ArrayList<Place> places, boolean status,
			User user) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.photos = photos;
		this.places = places;
		this.status = status;
		this.user = user;
	
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	

	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}

	public ArrayList<Place> getPlaces() {
		return places;
	}
	public void setPlaces(ArrayList<Place> places) {
		this.places = places;
	}
	
	
	
}
