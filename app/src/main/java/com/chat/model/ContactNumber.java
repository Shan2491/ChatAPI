package com.chat.model;

public class ContactNumber {
	
	private String countryCode;
	private String mobileNumber;
	
	public ContactNumber() {
		// TODO Auto-generated constructor stub
	}
	public ContactNumber(String countryCode, String mobileNumber) {
		super();
		this.countryCode = countryCode;
		this.mobileNumber = mobileNumber;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
