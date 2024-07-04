package com.coffeeorderproject.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.spring.coffeeorderproject.dto.UserDto;

@Mapper
public interface UserMapper {

	void insertUser(UserDto user);

	int idCheck(String id);

	UserDto selectUser(UserDto user);

	UserDto selectUserEmail(String id);

	void updateUserPw(String userId, String newPw);

	void updateUser(String userId);
	
}
