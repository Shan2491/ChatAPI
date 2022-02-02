package com.chat.util;

public class SQLHelper {
	
	public static final String CONNECTION_DETAILS = "jdbc:sqlserver://localhost:1433;database=MESSENGER;user=appuser1;password=password3";
	/* User Module Queries starts */
	public static final String GET_USER_ID_QUERY = "Select user_id from user_info where country_code = ? and mobile_number = ?";
	public static final String GET_USER_NAME_QUERY = "Select user_first_name, user_last_name from user_info where user_id = ?";
	public static final String INSERT_USER_INFO_QUERY = "Insert into user_info ( user_first_name, user_last_name, country_code, mobile_number, created_time) values (?,?,?,?,getdate())";
	public static final String INSERT_USER_OTP_QUERY = "Insert into user_login (user_id, otp, generated_time, expired_time) values (?,?,getdate(), DATEADD(minute,5,GETDATE()))";
	public static final String VERIFY_OTP_QUERY = "Select user_id from user_login where user_id = ? and OTP = ? and expired_time > getdate()";
	public static final String INSERT_TOKEN_QUERY = "INSERT INTO [dbo].[user_session] ([token] ,[user_id], [token_generated_time]) VALUES (?,?, GETDATE())";
	public static final String VALIDATE_TOKEN_QUERY = "select * from user_session where token = ? and token_expired_time is null";
	public static final String LOGOUT_SESSION = "update user_session set token_expired_time = GETDATE() where user_id = ? and token_expired_time is null";
	/* User Module Queries ends */

	/* Contact Module Queries starts */
	public static final String CONTACT_LIST_QUERY = "select contact_id, contact_first_name, contact_last_name, contact_user_id, blocked, country_code, mobile_number from contact a join user_info b on a.contact_user_id = b.user_id where a.created_user_id = ? order by contact_first_name asc";
	public static final String CREATE_CONTACT_QUERY = "Insert into contact (created_user_id, contact_first_name, contact_last_name, contact_user_id, created_time, blocked) values (?, ?, ?, ?, getdate(), ?)";
	public static final String DELETE_CONTACT_QUERY = "Delete from contact where contact_id = ?";
	public static final String DELETE_ALL_CONTACT_QUERY = "Delete from contact where created_user_id  = ?";
	public static final String BLOCK_CONTACT_QUERY = "Update contact set blocked = 'Y' where contact_id = ?";
	public static final String UNBLOCK_CONTACT_QUERY = "Update contact set blocked = 'N' where contact_id = ?";
	public static final String GET_USER_BY_CONTACTID_QUERY = "select contact_user_id from contact where contact_id = ?";
	/* Contact Module Queries ends */
	
	/* Chat Module Queries starts */
	public static final String ADD_NEW_MESSAGE_QUERY = "INSERT INTO [dbo].[message_history] ([chat_id], [sender_user_id], [receiver_user_id], [message_type], [text_message_content], [message_sent_time] , [message_status]) VALUES (?,?,?,?,?,GETDATE(),?)";
	public static final String ADD_NEW_CHAT_QUERY = "Insert into chat (primary_user_id, secondary_user_id) values (?, ?)";
	public static final String UPDATE_CHAT_QUERY = "Update chat set last_message_sent_time = getdate(), last_message_content = ?, last_message_by = ? where primary_user_id = ? and secondary_user_id = ?";
	public static final String CHECK_CHAT_EXISTS = "Select * from chat where primary_user_id = ? and secondary_user_id = ?";
	public static final String CHAT_LIST_QUERY = "SELECT [chat_id],[primary_user_id] ,[secondary_user_id] ,[last_message_sent_time] ,[last_message_content] ,[last_message_by]\r\n"
	+ "  FROM [dbo].[chat] where primary_user_id = ? or secondary_user_id = ? order by last_message_sent_time desc";
	public static final String CHAT_MESSAGES_QUERY = "select message_id, message_type, text_message_content, message_sent_time, sender_user_id"
	+ " from message_history where chat_id = ? order by message_sent_time desc";
	public static final String DELETE_MESSAGE = "Delete from message_history where message_id = ?";	
	/* Chat Module Queries starts */
	
}
