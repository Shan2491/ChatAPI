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
		if (checkUserExists(req.get("country_code"), req.get("mobile_number"))) {
			resultMessage = "User already exists!!!";
		} else {
			result = userRepo.createUser(user);

			if (result == 0) {
				resultMessage = "Insertion Failed";
			} else
				resultMessage = "User Created Successfully";
		}

		return resultMessage;

	}

	public boolean checkUserExists(String countryCode, String mobileNumber) {
		boolean result = false;
		if (userRepo.getUserId(countryCode, mobileNumber) == 0) {
			result = false;
		} else
			result = true;
		return result;
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

	public String deleteContact(int contactId) {

		int result;
		String resultMessage;
		result = contactRepo.deleteContact(contactId);
		if (result == 0) {
			resultMessage = "Contact not deleted";
		} else
			resultMessage = "Contact deleted successfully";
		return resultMessage;

	}

	public String blockContact(int contactId) {

		int result;
		String resultMessage;
		result = contactRepo.blockContact(contactId);
		if (result == 0) {
			resultMessage = "Contact not blocked";
		} else
			resultMessage = "Contact has been blocked successfully";
		return resultMessage;

	}

	public String unBlockContact(int contactId) {

		int result;
		String resultMessage;
		result = contactRepo.unBlockContact(contactId);
		if (result == 0) {
			resultMessage = "Contact not unblocked";
		} else
			resultMessage = "Contact has been unblocked successfully";
		return resultMessage;

	}

	public String deleteUser(int userId) {

		int result;
		String resultMessage;
		result = userRepo.deleteUser(userId);
		if (result == 0) {
			resultMessage = "User not deleted";
		} else {
			result = contactRepo.deleteAllContactsOfUser(userId);
			resultMessage = "User deleted successfully";
		}

		return resultMessage;

	}

}
