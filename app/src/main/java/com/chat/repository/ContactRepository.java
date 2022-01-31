
package com.chat.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		ArrayList<String> list = new ArrayList<String>();
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			// Create and execute a SELECT SQL statement.
			String selectSql =  "select contact_id, contact_first_name, contact_last_name, contact_user_id, blocked, country_code, mobile_number "
					+ "from contact a join user_info b on a.contact_user_id = b.user_id "
					+ "where a.created_user_id = ?";
			PreparedStatement statement = connection.prepareStatement(selectSql);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
			// Print results from select statement
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
	
	public boolean createUser(User user) {
		boolean result = false;
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			// Create and execute a SELECT SQL statement.
			String selectSql =  "Insert into user_info ( user_id, user_first_name, user_last_name, country_code, mobile_number, created_time) values (?,?,?,?, ?,getdate())"
					+ "";
			PreparedStatement statement = connection.prepareStatement(selectSql);
			statement.setString(1, "123");
			statement.setString(2, user.getUserFirstName());
			statement.setString(3, user.getUserLastName());
			statement.setString(4, user.getContactNumber().getCountryCode());
			statement.setString(5, user.getContactNumber().getMobileNumber());
			
			
			result = statement.execute();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}

}
