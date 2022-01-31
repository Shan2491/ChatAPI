package com.chat.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.chat.model.User;

@Repository
public class UserRepository {
	String connectionUrl = "jdbc:sqlserver://localhost:1433;database=MESSENGER;user=appuser1;password=password3;";
	

	public int getUserId(String countryCode, String mobileNumber) {
		int userId = 0;
		ResultSet resultSet = null;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			String selectSql =  "Select user_id from user_info where country_code = ? and mobile_number = ?";
			PreparedStatement statement = connection.prepareStatement(selectSql);
			statement.setString(1, countryCode);
			statement.setString(2, mobileNumber);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				userId = resultSet.getInt("user_id");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
		
	}
	
	public int createUser(User user) {
		int result = 0;
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
			
			
			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
}
