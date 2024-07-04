package com.spring.coffeeorderproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.spring.coffeeorderproject.dto.UserDto;
import com.spring.coffeeorderproject.service.AccountService;

import lombok.Setter;

public class MySqlAccountDaoWithMyBatis implements AccountDao {
	
//	@Setter
//	private JdbcTemplate jdbcTemplate;
	
	@Setter
	private SqlSessionTemplate sqlSessionTemplate;
	
	private final String USER_MAPPER = "com.coffeeorderproject.mapper.UserMapper.";
	
	@Override
	public void insertUser(UserDto user) {
		sqlSessionTemplate.insert(USER_MAPPER + "insertUser", user);
	}
	
	@Override
	public int idCheck(String id) {
		int isHave = sqlSessionTemplate.selectOne(USER_MAPPER + "idCheck", id);
		return isHave;
	}
	
	@Override
	public UserDto selectUser(UserDto user) {
		UserDto selectedMember = sqlSessionTemplate.selectOne(USER_MAPPER + "selectUser", user);
		return selectedMember;
	}
	
	@Override
	public UserDto selectUserEmail(String id) {
		UserDto selectedMember = sqlSessionTemplate.selectOne(USER_MAPPER + "selectUserEmail", id);
		return selectedMember;
	}
	
	@Override
	public void updateUserPw(String userId, String newPw) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("newPw", newPw);
		sqlSessionTemplate.update(USER_MAPPER + "updateUserPw", params);
	}
	
	@Override
	public void updateUser(String userId) {
		sqlSessionTemplate.update(USER_MAPPER + "updateUser", userId);
	}	
	
}