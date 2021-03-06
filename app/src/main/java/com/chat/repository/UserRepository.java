package com.chat.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.chat.model.User;
import com.chat.util.SQLHelper;

@Repository
public class UserRepository {
	
	String connectionUrl = SQLHelper.CONNECTION_DETAILS;

	public int getUserId(String countryCode, String mobileNumber) {
		int userId = 0;
		ResultSet resultSet = null;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.GET_USER_ID_QUERY);
			statement.setString(1, countryCode);
			statement.setString(2, mobileNumber);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				userId = resultSet.getInt("user_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;

	}

	public int createUser(User user) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.INSERT_USER_INFO_QUERY);
			statement.setString(1, user.getUserFirstName());
			statement.setString(2, user.getUserLastName());
			statement.setString(3, user.getContactNumber().getCountryCode());
			statement.setString(4, user.getContactNumber().getMobileNumber());

			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int loginUser(int userId, String OTP) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.INSERT_USER_OTP_QUERY);
			System.out.println("userId" + userId + "otp" + OTP);
			statement.setInt(1, userId);
			statement.setString(2, OTP);
			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public boolean verifyOTP(int userId, String OTP) {
		boolean validUser = false;
		ResultSet resultSet = null;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.VERIFY_OTP_QUERY);
			statement.setInt(1, userId);
			statement.setString(2, OTP);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				validUser = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return validUser;
	}

	public int deleteUser(int userId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}

			String selectSql = "Delete from user_info where user_id = ?";
			PreparedStatement statement = connection.prepareStatement(selectSql);
			statement.setInt(1, userId);

			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int createUserSession(int userId, String token) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.INSERT_TOKEN_QUERY);
			statement.setString(1, token);
			statement.setInt(2, userId);
			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int validateToken(String token) {
		int userId = 0;
		ResultSet resultSet = null;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.VALIDATE_TOKEN_QUERY);
			statement.setString(1, token);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				userId = resultSet.getInt("user_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}
	
	public String getUserName(int userId) {
		String userName = "";
		ResultSet resultSet = null;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}
			PreparedStatement statement = connection.prepareStatement(SQLHelper.GET_USER_NAME_QUERY);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				userName = resultSet.getString("user_first_name") + " " + resultSet.getString("user_last_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userName;

	}
	
	public int logoutUser(int userId) {
		int result = 0;
		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			if (connection != null) {
				System.out.println("Connected to DB");
			}

			PreparedStatement statement = connection.prepareStatement(SQLHelper.LOGOUT_SESSION);
			statement.setInt(1, userId);

			result = statement.executeUpdate();
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

}
