package com.spring.coffeeorderproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.spring.coffeeorderproject.dto.UserDto;
import com.spring.coffeeorderproject.service.AccountService;

import lombok.Setter;

public class MySqlAccountDao implements AccountDao {
	
	@Setter
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insertUser(UserDto user) {
		
		String sql = "INSERT INTO user (userid, username, usernickname, userphone, useremail, userpw) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, user.getUserId(), user.getUserName(), user.getUserNickname(), user.getUserPhone(), user.getUserEmail(), user.getUserPw());
		
	}
	
	@Override
	public int idCheck(String id) {
		
		String sql = "SELECT COUNT(userid) from user WHERE userid = ?";
		
		int isHave = 
				jdbcTemplate.queryForObject(sql,
											new RowMapper<Integer>() {

												@Override
												public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
													return rs.getInt(1);
													
												}
											
											},
											id);
		return isHave;
	}
	
	@Override
	public UserDto selectUser(UserDto user) {
		// c.userid IS NOT NULL << (쿠폰 있으면1 없으면0)
		String sql = "SELECT u.userid, username, usernickname, userphone, useremail, userpw, useradmin, userregidate, useractive, c.couponid "
				+ "FROM user u "
				+ "LEFT JOIN coupon c ON u.userid = c.userid "
				+ "WHERE u.userid = ? AND u.userpw = ? AND u.useractive = false";	
		UserDto selectedMember =
				jdbcTemplate.queryForObject(sql, 
						new RowMapper<UserDto>() {

							@Override
							public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {

								UserDto member = new UserDto();
								member.setUserId(rs.getString(1));
								member.setUserName(rs.getString(2));
								member.setUserNickname(rs.getString(3));
								member.setUserPhone(rs.getString(4));
								member.setUserEmail(rs.getString(5));
								member.setUserPw(rs.getString(6));
								member.setUserAdmin(rs.getBoolean(7));
								member.setUserRegidate(rs.getDate(8));
								member.setUserActive(rs.getBoolean(9));
								member.setCouponId(rs.getInt(10));
								
								return member;
							}
						
						}, 
						user.getUserId(), user.getUserPw());

		return selectedMember;
	}
	
	@Override
	public UserDto selectUserEmail(String id) {
		
		String sql = "SELECT useremail from user WHERE userid = ?";
		
		UserDto selectedMember = 
				jdbcTemplate.queryForObject(sql, 
						new RowMapper<UserDto>() {

							@Override
							public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
								
								UserDto member = new UserDto();
								member.setUserEmail(rs.getString(1));
								
								return member;
							}
						
						},
						id);
			
		return selectedMember;
		
	}
	
	@Override
	public void updateUserPw(String userId, String newPw) {
		
		String sql = "UPDATE user SET userpw = ? WHERE userid = ?";
		jdbcTemplate.update(sql, userId, newPw);
			
	}
	
	@Override
	public void updateUser(String userId) {
		
		String sql = "UPDATE user SET useractive = 1 WHERE userid = ? ";
		jdbcTemplate.update(sql, userId);
			
	}	
	
}