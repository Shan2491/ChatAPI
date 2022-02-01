package com.chat.model;

public class Contact {
	
	private String contactFirstName;
	private String contactLastName;
	private ContactNumber contactNumber;
	private int contactId;
	private String contactProfilePic;
	private char blocked;
	
	public Contact(String contactFirstName, String contactLastName, int contactId, String contactProfilePic,
			char blocked) {
		super();
		this.contactFirstName = contactFirstName;
		this.contactLastName = contactLastName;
		this.contactId = contactId;
		this.contactProfilePic = contactProfilePic;
		this.blocked = blocked;
	}
	public Contact() {
		// TODO Auto-generated constructor stub
	}
	public String getContactFirstName() {
		return contactFirstName;
	}
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}
	public String getContactLastName() {
		return contactLastName;
	}
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public ContactNumber getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(ContactNumber contactNumber) {
		this.contactNumber = contactNumber;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getContactProfilePic() {
		return contactProfilePic;
	}
	public void setContactProfilePic(String contactProfilePic) {
		this.contactProfilePic = contactProfilePic;
	}
	public char getBlocked() {
		return blocked;
	}
	public void setBlocked(char blocked) {
		this.blocked = blocked;
	}
	

}
