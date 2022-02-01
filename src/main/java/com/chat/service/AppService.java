package com.chat.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

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

	public String loginUser(String countryCode, String mobileNumber) {
		String message;
		int userId = userRepo.getUserId(countryCode, mobileNumber);
		if (userId == 0) {
			message = "Invalid User!!!";
		} else {
			int otpInsert = userRepo.loginUser(userId, generateOTP());
			if (otpInsert == 0) {
				message = "OTP insert failed";
			} else {
				message = "OTP sent to your mobile, Please use that for login";
			}
		}
		return message;
	}

	public String validateUser(String countryCode, String mobileNumber, String OTP) {
		String token;
		int userId = userRepo.getUserId(countryCode, mobileNumber);
		if (userId == 0) {
			token = "Invalid User!!!";
		} else {
			if (verifyOTP(userId, OTP)) {
				token = generateRandomToken(15);
				userRepo.createUserSession(userId, token);

			} else {
				token = "Invalid OTP";
			}
		}
		return token;
	}

	private static String generateOTP() {
		String numbers = "1234567890";
		Random random = new Random();
		char[] otp = new char[4];
		StringBuffer otpString = new StringBuffer();
		for (int i = 0; i < otp.length; i++) {
			otp[i] = numbers.charAt(random.nextInt(numbers.length()));
			otpString = otpString.append(otp[i]);
		}
		return otpString.toString();
	}

	public static String generateRandomToken(int len) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}

	private boolean verifyOTP(int userId, String OTP) {
		return userRepo.verifyOTP(userId, OTP);
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
