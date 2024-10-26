package com.frightfox.poc.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PincodeLocation {
    @Id
    private String pincode;
    private double latitude;
    private double longitude;
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public PincodeLocation(String pincode, double latitude, double longitude) {
		super();
		this.pincode = pincode;
		this.latitude = latitude;
		this.longitude = longitude;
	}

    // Constructors, getters, and setters
    
}
