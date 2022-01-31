package com.chat.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.model.Chat;
import com.chat.model.Message;
import com.chat.repository.ChatRepository;
import com.chat.repository.ContactRepository;

@Service
public class ChatService {

	@Autowired
	private ChatRepository chatRepo;

	@Autowired
	private ContactRepository contactRepo;

	public String sendMessage(Map<String, String> req, int chatId) {

		int result;
		String resultMessage;
		int contactUserId = getUseridforContact(Integer.parseInt(req.get("contact_id")));
		result = chatRepo.addNewMessage(req, contactUserId, chatId);
		if (result == 0) {
			resultMessage = "Message sent failed";
		} else {
			resultMessage = "Message Sent Successfully";
			updateChat(req, contactUserId);
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

	public void updateChat(Map<String, String> req, int contactUserId) {
		int primaryId = 0;
		int secondaryId = 0;
		int senderId = Integer.parseInt(req.get("sender_user_id"));
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
		if (userId > contactId) {
			primaryId = userId;
			secondaryId = contactId;
		} else {
			primaryId = contactId;
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
