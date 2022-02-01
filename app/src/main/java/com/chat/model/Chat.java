package com.chat.model;

import java.sql.Date;

public class Chat {

	private int chatId;
	private int contactUserId;
	private String lastMessageContent;
	private int lastMessageBy;
	private Date lastMessageSentTime;

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public String getLastMessageContent() {
		return lastMessageContent;
	}

	public void setLastMessageContent(String lastMessageContent) {
		this.lastMessageContent = lastMessageContent;
	}

	public int getLastMessageBy() {
		return lastMessageBy;
	}

	public void setLastMessageBy(int lastMessageBy) {
		this.lastMessageBy = lastMessageBy;
	}

	public Date getLastMessageSentTime() {
		return lastMessageSentTime;
	}

	public void setLastMessageSentTime(Date lastMessageSentTime) {
		this.lastMessageSentTime = lastMessageSentTime;
	}

	public int getContactUserId() {
		return contactUserId;
	}

	public void setContactUserId(int contactUserId) {
		this.contactUserId = contactUserId;
	}

}
