package com.chat.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.chat.model.Chat;
import com.chat.model.Message;
import com.chat.util.SQLHelper;

@Repository
public class ChatRepository {

	String connectionUrl = SQLHelper.CONNECTION_DETAILS;

	public int addNewMessage(Map<String, String> input, int receiverUserId, int chatId, int userId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.ADD_NEW_MESSAGE_QUERY);
			statement.setInt(1, chatId);
			statement.setInt(2, userId);
			statement.setInt(3, receiverUserId);
			statement.setString(4, input.get("message_type"));
			statement.setString(5, input.get("message_content"));
			statement.setString(6, "Sent");

			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int addChat(int primaryUserId, int secondaryUserId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.ADD_NEW_CHAT_QUERY);
			statement.setInt(1, primaryUserId);
			statement.setInt(2, secondaryUserId);

			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int updateChat(int primaryUserId, int secondaryUserId, String messageContent, int sentBy) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}

			PreparedStatement statement = connection.prepareStatement(SQLHelper.UPDATE_CHAT_QUERY);
			statement.setString(1, messageContent);
			statement.setInt(2, sentBy);
			statement.setInt(3, primaryUserId);
			statement.setInt(4, secondaryUserId);
			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public boolean checkChatExists(int primaryUserId, int secondaryUserId) {
		boolean chatExists = false;
		ResultSet resultSet = null;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.CHECK_CHAT_EXISTS);
			statement.setInt(1, primaryUserId);
			statement.setInt(2, secondaryUserId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				chatExists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chatExists;

	}

	public ArrayList<Chat> chatList(int userId) {
		ResultSet resultSet = null;
		Chat chat = new Chat();
		ArrayList<Chat> chatList = new ArrayList<Chat>();
		int primaryUserId = 0;
		int SecondaryUserId = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.CHAT_LIST_QUERY);
			statement.setInt(1, userId);
			statement.setInt(2, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				chat = new Chat();
				chat.setChatId(Integer.parseInt(resultSet.getString("chat_id")));
				primaryUserId = Integer.parseInt(resultSet.getString("primary_user_id"));
				SecondaryUserId = Integer.parseInt(resultSet.getString("secondary_user_id"));
				if (primaryUserId == userId) {
					chat.setContactUserId(SecondaryUserId);
				} else {
					chat.setContactUserId(primaryUserId);
				}
				chat.setLastMessageBy(Integer.parseInt(resultSet.getString("last_message_by")));
				chat.setLastMessageContent(resultSet.getString("last_message_content"));
				chat.setLastMessageSentTime(resultSet.getTimestamp("last_message_sent_time"));
				chatList.add(chat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chatList;

	}

	public ArrayList<Message> getChatMessages(int chatId) {

		ResultSet resultSet = null;
		Message message = new Message();
		ArrayList<Message> chatHistory = new ArrayList<Message>();
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.CHAT_MESSAGES_QUERY);
			statement.setInt(1, chatId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				message = new Message();
				message.setMessageId(resultSet.getInt("message_id"));
				message.setMessageType(resultSet.getString("message_type"));
				message.setMessage_content(resultSet.getString("text_message_content"));
				message.setMessageSentTime(resultSet.getTimestamp("message_sent_time"));
				message.setSenderUserId(resultSet.getInt("sender_user_id"));
				chatHistory.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chatHistory;
	}

	public int deleteChatMessage(int userId, int messageId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}

			PreparedStatement statement = connection.prepareStatement(SQLHelper.DELETE_MESSAGE);
			statement.setInt(1, messageId);

			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

}
