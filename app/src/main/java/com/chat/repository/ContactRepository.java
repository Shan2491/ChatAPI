
package com.chat.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chat.model.Contact;
import com.chat.model.ContactNumber;
import com.chat.model.User;

@Repository
public class ContactRepository {

	String connectionUrl = "jdbc:sqlserver://localhost:1433;database=MESSENGER;user=appuser1;password=password3;";

	public ArrayList<Contact> getContactsList(int userId) {

		ArrayList<Contact> contactList = new ArrayList();
		Contact contact;
		ContactNumber contactNumber;
		ResultSet resultSet = null;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}

			String selectSql = "select contact_id, contact_first_name, contact_last_name, contact_user_id, blocked, country_code, mobile_number "
					+ "from contact a join user_info b on a.contact_user_id = b.user_id "
					+ "where a.created_user_id = ?";
			PreparedStatement statement = connection.prepareStatement(selectSql);
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

	public int createContact(Map<String, String> input, int contactUserId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}

			String selectSql = "Insert into contact (created_user_id, contact_first_name, contact_last_name, contact_user_id, created_time, blocked) "
					+ "values (?, ?, ?, getdate(), ?) ";
			PreparedStatement statement = connection.prepareStatement(selectSql);
			statement.setString(1, input.get("user_id"));
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
	
	public int deleteContact(int userId, int contactId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}

			String selectSql = "Delete from contact where contact_id = ?";
			PreparedStatement statement = connection.prepareStatement(selectSql);
			statement.setInt(1, contactId);

			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}


}
