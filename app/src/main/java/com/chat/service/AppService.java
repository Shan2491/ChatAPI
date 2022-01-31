package com.chat.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.model.Contact;
import com.chat.model.ContactNumber;
import com.chat.model.User;
import com.chat.repository.ContactRepository;
import com.chat.repository.UserRepository;

@Service
public class AppService {

	@Autowired
	private ContactRepository contactRepo;

	@Autowired
	private UserRepository userRepo;

	public ArrayList<Contact> contactList(int userId) {
		return contactRepo.getContactsList(userId);

	}

	public String createUser(Map<String, String> req) {
		User user = new User();
		ContactNumber contactNo = new ContactNumber();
		int result;
		String resultMessage;

		user.setUserFirstName(req.get("first_name"));
		user.setUserLastName(req.get("last_name"));
		contactNo.setCountryCode(req.get("country_code"));
		contactNo.setMobileNumber(req.get("mobile_number"));
		user.setContactNumber(contactNo);

		result = userRepo.createUser(user);

		if (result == 0) {
			resultMessage = "Insertion Failed";
		} else
			resultMessage = "User Created Successfully";

		return resultMessage;

	}

	public String createContact(Map<String, String> req) {

		int result;
		String resultMessage;
		int contactUserId = userRepo.getUserId(req.get("country_code"), req.get("mobile_number"));
		if (contactUserId == 0) {
			result = contactRepo.createContact(req, contactUserId);
			if (result == 0) {
				resultMessage = "Contact Creation Failed";
			} else
				resultMessage = "Contact Created Successfully";
		} else
			resultMessage = "User doesn't exist in app chat";

		return resultMessage;

	}
	

	public String deleteContact(int userId, int contactId) {

		int result;
		String resultMessage;
		result = contactRepo.deleteContact(userId, contactId);
		if(result == 0) {
			resultMessage = "Contact not deleted";
		}
		else 
			resultMessage = "Contact deleted successfully";
		return resultMessage;

	}

}
