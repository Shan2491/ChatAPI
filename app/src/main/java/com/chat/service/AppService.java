package com.chat.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.model.Contact;
import com.chat.model.ContactNumber;
import com.chat.model.User;
import com.chat.repository.ContactRepository;

@Service
public class AppService {

	@Autowired
	private ContactRepository contactRepo;

	public ArrayList<Contact> contactList(int userId){
		return contactRepo.getContactsList(userId);
		
	}
	
	public boolean createUser(Map<String, String> req){
		User user = new User();
		ContactNumber contactNo = new ContactNumber();
		user.setUserFirstName(req.get("first_name"));
		user.setUserLastName(req.get("last_name"));
		contactNo.setCountryCode(req.get("country_code"));
		contactNo.setMobileNumber(req.get("mobile_number"));
		user.setContactNumber(contactNo);
		return contactRepo.createUser(user);
		
	}

}
