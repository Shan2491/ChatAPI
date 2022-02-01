package com.chat.model;

import java.sql.Date;

public class Message {
	private int messageId;
	private String messageType;
	private String message_content;
	private Date messageSentTime;
	private int senderUserId;
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public Date getMessageSentTime() {
		return messageSentTime;
	}
	public void setMessageSentTime(Date messageSentTime) {
		this.messageSentTime = messageSentTime;
	}
	public int getSenderUserId() {
		return senderUserId;
	}
	public void setSenderUserId(int senderUserId) {
		this.senderUserId = senderUserId;
	}
	

}
