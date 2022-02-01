package com.chat.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.chat.model.Chat;
import com.chat.model.MessageContent;
import com.chat.model.Message;
import com.chat.repository.ChatRepository;
import com.chat.repository.ContactRepository;
import com.chat.repository.UserRepository;

@Service
public class ChatService {

	@Autowired
	private ChatRepository chatRepo;

	@Autowired
	private ContactRepository contactRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private SimpMessagingTemplate template;

	public String sendMessage(Map<String, String> req, int chatId, int userId) {

		int result;
		String resultMessage;
		int contactUserId = getUseridforContact(Integer.parseInt(req.get("contactId")));
		result = chatRepo.addNewMessage(req, contactUserId, chatId, userId);
		if (result == 0) {
			resultMessage = "Message sent failed";
		} else {
			resultMessage = "Message Sent Successfully";
			String userName = userRepo.getUserName(userId);
			String message = "New Message from " + userName + " \""+  req.get("message_content") + "\"";
			 this.template.convertAndSend("/topic/messages", new MessageContent(message));
			updateChat(req, contactUserId, userId);
		}

		return resultMessage;

	}

	public int getUseridforContact(int contactId) {
		return contactRepo.getUserbyContactId(contactId);
	}

	public ArrayList<Message> getChatMessages(int chatId) {
		return chatRepo.getChatMessages(chatId);
	}

	public boolean checkChatExists(int primaryId, int secondaryId) {
		return chatRepo.checkChatExists(primaryId, secondaryId);
	}

	public void updateChat(Map<String, String> req, int contactUserId, int senderId) {
		int primaryId = 0;
		int secondaryId = 0;
		if (senderId > contactUserId) {
			primaryId = senderId;
			secondaryId = contactUserId;
		} else {
			primaryId = contactUserId;
			secondaryId = senderId;
		}
		chatRepo.updateChat(primaryId, secondaryId, req.get("message_content"), senderId);

	}

	public ArrayList<Chat> chatHistory(int userId) {
		return chatRepo.chatList(userId);
	}

	public String createChat(int userId, int contactId) {
		int result = 0;
		int primaryId = 0;
		int secondaryId = 0;
		String resultMessage;
		int contactUserId = contactRepo.getUserbyContactId(contactId);
		if (userId > contactUserId) {
			primaryId = userId;
			secondaryId = contactUserId;
		} else {
			primaryId = contactUserId;
			secondaryId = userId;
		}
		result = chatRepo.addChat(primaryId, secondaryId);
		if (result == 0) {
			resultMessage = "Chat creation failed";
		} else {
			resultMessage = "Chat created successfully";
		}
		return resultMessage;
	}

	public String deleteChatMessage(int userId, int messageId) {

		int result;
		String resultMessage;
		result = chatRepo.deleteChatMessage(userId, messageId);
		if (result == 0) {
			resultMessage = "Message not deleted";
		} else {
			resultMessage = "Message deleted successfully";
		}

		return resultMessage;

	}

}
