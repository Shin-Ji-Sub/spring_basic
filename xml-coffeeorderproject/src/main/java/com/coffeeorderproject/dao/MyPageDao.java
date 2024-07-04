package com.coffeeorderproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import user.coffeeorderproject.dto.UserDto;

public class MyPageDao {
	
	

	public void updateUserInfo(UserDto user) {

		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "UPDATE user SET userpw = ?, usernickname = ?, userphone = ?, useremail = ? WHERE userid = ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, user.getUserPw());
			pstmt.setString(2, user.getUserNickname());
			pstmt.setString(3, user.getUserPhone());
			pstmt.setString(4, user.getUserEmail());
			pstmt.setString(5, user.getUserId());
			
			// 4. 명령 실행
			pstmt.executeUpdate();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
	}	
}