package com.chat.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chat.model.Chat;
import com.chat.model.Message;

@Repository
public class ChatRepository {
	String connectionUrl = "jdbc:sqlserver://localhost:1433;database=MESSENGER;user=appuser1;password=password3;";

	public int addNewMessage(Map<String, String> input, int receiverUserId, int chatId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}

			String selectSql = "INSERT INTO [dbo].[message_history] ([chat_id], [sender_user_id], [receiver_user_id], [message_type], [text_message_content], [message_sent_time] , [message_status])\r\n"
					+ "     VALUES (?,?,?,?,GETDATE(),?)";
			PreparedStatement statement = connection.prepareStatement(selectSql);
			statement.setInt(1, chatId);
			statement.setInt(2, Integer.parseInt(input.get("sender_user_id")));
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

			String selectSql = "Insert into chat (primary_user_id, secondary_user_id) " + "values (?, ?)";
			PreparedStatement statement = connection.prepareStatement(selectSql);
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

			String selectSql = "Update chat set last_message_sent_time = getdate(), last_message_content = ?, last_message_by = ? "
					+ "where primary_user_id = ? and secondary_user_id = ?";
			PreparedStatement statement = connection.prepareStatement(selectSql);
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
			String selectSql = "Select * from chat where primary_user_id = ? and secondary_user_id = ?";
			PreparedStatement statement = connection.prepareStatement(selectSql);
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
			String selectSql = "SELECT [chat_id],[primary_user_id] ,[secondary_user_id] ,[last_message_sent_time] ,[last_message_content] ,[last_message_by]\r\n"
					+ "  FROM [dbo].[chat] where primary_user_id = ? or secondary_user_id = ?";
			PreparedStatement statement = connection.prepareStatement(selectSql);
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
				chat.setLastMessageSentTime(resultSet.getDate("last_message_sent_time"));
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
			String selectSql = "select message_id, message_type, text_message_content from message_history where chat_id = ?";
			PreparedStatement statement = connection.prepareStatement(selectSql);
			statement.setInt(1, chatId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				message = new Message();
				message.setMessageId(resultSet.getInt("message_id"));
				message.setMessageType(resultSet.getString("message_type"));
				message.setMessage_content(resultSet.getString("text_message_content"));
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

			String selectSql = "Delete from message_history where message_id = ?";
			PreparedStatement statement = connection.prepareStatement(selectSql);
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
