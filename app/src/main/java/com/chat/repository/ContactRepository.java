
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

import com.chat.model.Contact;
import com.chat.model.ContactNumber;
import com.chat.util.SQLHelper;

@Repository
public class ContactRepository {
	
	String connectionUrl = SQLHelper.CONNECTION_DETAILS;

	public ArrayList<Contact> getContactsList(int userId) {

		ArrayList<Contact> contactList = new ArrayList<Contact>();
		Contact contact;
		ContactNumber contactNumber;
		ResultSet resultSet = null;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}

			PreparedStatement statement = connection.prepareStatement(SQLHelper.CONTACT_LIST_QUERY);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				contact = new Contact();
				contactNumber = new ContactNumber();
				contact.setContactFirstName(resultSet.getString("contact_first_name"));
				contact.setContactLastName(resultSet.getString("contact_last_name"));
				contact.setContactId(resultSet.getInt("contact_id"));
				contact.setBlocked(resultSet.getString("blocked").charAt(0));
				contactNumber.setCountryCode(resultSet.getString("country_code"));
				contactNumber.setMobileNumber(resultSet.getString("mobile_number"));
				contact.setContactNumber(contactNumber);
				contactList.add(contact);
			}
			System.out.println("No of records retrieved from DB: " + contactList.size());
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}

	public int createContact(Map<String, String> input, int contactUserId, int userId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.CREATE_CONTACT_QUERY);
			statement.setInt(1, userId);
			statement.setString(2, input.get("first_name"));
			statement.setString(3, input.get("last_name"));
			statement.setInt(4, contactUserId);
			statement.setString(5, "N");

			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int deleteContact(int contactId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}

			PreparedStatement statement = connection.prepareStatement(SQLHelper.DELETE_CONTACT_QUERY);
			statement.setInt(1, contactId);

			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int deleteAllContactsOfUser(int userId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.DELETE_ALL_CONTACT_QUERY);
			statement.setInt(1, userId);

			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int blockContact(int contactId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}

			PreparedStatement statement = connection.prepareStatement(SQLHelper.BLOCK_CONTACT_QUERY);
			statement.setInt(1, contactId);

			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int unBlockContact(int contactId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.UNBLOCK_CONTACT_QUERY);
			statement.setInt(1, contactId);
			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int getUserbyContactId(int contactId) {
		ResultSet resultSet = null;
		int contactUserId = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.GET_USER_BY_CONTACTID_QUERY);
			statement.setInt(1, contactId);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				contactUserId = resultSet.getInt("contact_user_id");
			}
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return contactUserId;

	}

}
