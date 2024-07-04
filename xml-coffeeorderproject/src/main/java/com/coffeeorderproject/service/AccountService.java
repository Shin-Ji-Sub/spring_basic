package com.coffeeorderproject.service;

import java.util.ArrayList;

import com.coffeeorderproject.dto.CartDto;
import com.coffeeorderproject.dto.UserDto;

public interface AccountService {

	void inputUser(UserDto user);

	int checkId(String id);

	UserDto signInUser(UserDto user);

	UserDto getUserEmail(String id);

	void changeUserPw(String userId, String newPw);

	ArrayList<CartDto> getUserCart(String userId);

}