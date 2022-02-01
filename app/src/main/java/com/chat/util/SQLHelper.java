package com.chat.util;

public class SQLHelper {
	public static final String GET_USER_ID_QUERY = "Select user_id from user_info where country_code = ? and mobile_number = ?";
	public static final String INSERT_USER_INFO_QUERY = "Insert into user_info ( user_first_name, user_last_name, country_code, mobile_number, created_time) values (?,?,?,?,getdate())";
	public static final String INSERT_USER_OTP_QUERY = "Insert into user_login (user_id, otp, generated_time, expired_time) values (?,?,getdate(), DATEADD(minute,5,GETDATE()))";
	public static final String VERIFY_OTP_QUERY = "Select user_id from user_login where user_id = ? and OTP = ? and expired_time > getdate()";
	public static final String INSERT_TOKEN_QUERY = "INSERT INTO [dbo].[user_session] ([token] ,[user_id]) VALUES (?,?)";
	public static final String VALIDATE_TOKEN_QUERY = "select * from user_session where token = ?";
}
