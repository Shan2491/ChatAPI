package com.chat.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.model.Chat;
import com.chat.model.Contact;
import com.chat.model.Message;
import com.chat.service.AppService;
import com.chat.service.ChatService;

@RestController
public class AppController {

	@Autowired
	private AppService appService;

	@Autowired
	private ChatService chatService;

	@RequestMapping("/")
	String hello() {
		return "Welcome to chat app";
	}

	@PostMapping("/users")
	public String createUser(@RequestBody Map<String, String> req) throws Exception {
		return appService.createUser(req);
	}
	
	@PostMapping("/users/login")
	public String loginUser(@RequestBody Map<String, String> req) throws Exception {
		String countryCode = req.get("countryCode");
		String mobileNumber = req.get("mobileNumber");
		if(null != countryCode && null != mobileNumber && !"".equalsIgnoreCase(countryCode) && !"".equalsIgnoreCase(mobileNumber)) {
					return appService.loginUser(countryCode, mobileNumber);
		}
		return "Invalid Credentials";
	}

	@PostMapping("/users/OTP")
	public String validateUser(@RequestBody Map<String, String> req) throws Exception {
		String countryCode = req.get("countryCode");
		String mobileNumber = req.get("mobileNumber");
		String OTP = req.get("OTP");
		if(null != countryCode && null != mobileNumber && !"".equalsIgnoreCase(countryCode) && !"".equalsIgnoreCase(mobileNumber)
				&& null != OTP && !"".equalsIgnoreCase(OTP)) {
					return appService.validateUser(countryCode, mobileNumber, OTP);
		}
		return "Invalid Credentials";
	}
	
	@DeleteMapping("/users/{userId}")
	public String deleteUser(@PathVariable("userId") int userId) throws Exception {
		return appService.deleteUser(userId);
	}

	@PostMapping("/contacts")
	public String createContact(@RequestBody Map<String, String> req, @RequestAttribute int userId) throws Exception {
		return appService.createContact(req, userId);
	}

	@GetMapping("/contacts")
	public ArrayList<Contact> getContactList(ServletRequest req, @RequestAttribute int userId) throws Exception {
		return appService.contactList(userId);
	}

	@DeleteMapping("/contacts/{contactId}")
	public String deleteContact(@PathVariable("contactId") int contactId) throws Exception {
		return appService.deleteContact(contactId);
	}

	@PutMapping("/contacts/block/{contactId}")
	public String blockContact(@PathVariable("contactId") int contactId) throws Exception {
		return appService.blockContact(contactId);
	}

	@PutMapping("/contacts/unblock/{contactId}")
	public String unBlockContact(@PathVariable("contactId") int contactId) throws Exception {
		return appService.unBlockContact(contactId);
	}

	@PostMapping("/chats")
	public String createChat(@RequestBody Map<String, String> req, @RequestAttribute int userId)
			throws Exception {
		System.out.println("userId" + userId + "contactId" + Integer.parseInt(req.get("contactId")));
		return chatService.createChat(userId, Integer.parseInt(req.get("contactId")));
	}

	@GetMapping("/chats")
	public ArrayList<Chat> getChatHistory(@RequestAttribute int userId) throws Exception {
		return chatService.chatHistory(userId);
	}

	@PostMapping("/chats/{chatId}/messages")
	public String sendMessage(@RequestBody Map<String, String> req, @PathVariable("chatId") int chatId, @RequestAttribute int userId)
			throws Exception {
		return chatService.sendMessage(req, chatId, userId);
	}

	@GetMapping("/chats/{chatId}/messages")
	public ArrayList<Message> getMessages(@PathVariable("chatId") int chatId) throws Exception {
		return chatService.getChatMessages(chatId);
	}

	@DeleteMapping("/messages/{userId}/{messageId}")
	public String deleteChatMessage(@PathVariable("userId") int userId, @PathVariable("messageId") int messageId)
			throws Exception {
		return chatService.deleteChatMessage(userId, messageId);
	}

}