package com.spring.coffeeorderproject.service;


import java.util.ArrayList;

import com.spring.coffeeorderproject.common.Util;
import com.spring.coffeeorderproject.dao.AccountDao;
import com.spring.coffeeorderproject.dao.CartDao;
import com.spring.coffeeorderproject.dao.CouponDao;
import com.spring.coffeeorderproject.dto.CartDto;
import com.spring.coffeeorderproject.dto.UserCouponDto;
import com.spring.coffeeorderproject.dto.UserDto;

import lombok.Setter;

public class AccountServiceImpl implements AccountService {
	
	@Setter
	private AccountDao accountDao;
	
	// 회원가입
	@Override
	public void inputUser(UserDto user) {
		// 비번 암호화
		String hashedPasswd = Util.getHashedString(user.getUserPw(), "SHA-256");
		user.setUserPw(hashedPasswd);
		
		accountDao.insertUser(user);
	}
	
	// 중복 아이디 검사
	@Override
	public int checkId(String id) {
		int isHave = accountDao.idCheck(id);
		return isHave;
	}
	
	//로그인
	@Override
	public UserDto signInUser(UserDto user) {
		String hashedPasswd = Util.getHashedString(user.getUserPw(), "SHA-256");
		user.setUserPw(hashedPasswd);
		
		UserDto member = accountDao.selectUser(user);
		
		if(member != null) { // 정상 로그인이 아닐 경우 
			// 쿠폰 테이블에 로그인 유저의 데이터가 있는지 조회 (쿠폰이 있는지)
			CouponDao coupon = new CouponDao();
			ArrayList<UserCouponDto> couponList = coupon.selectCouponList(user);
			member.setUsercoupon(couponList);
			
			return member;
		}
		

		return member;
	}
	
	
	
	// 사용자에게 Email 보내기
	@Override
	public UserDto getUserEmail(String id) {
		UserDto member = accountDao.selectUserEmail(id);
		return member;
	}
	
	// 사용자 PW Update
	@Override
	public void changeUserPw(String userId, String newPw) {
		// 비번 암호화
		String hashedPasswd = Util.getHashedString(newPw, "SHA-256");
//		user.setUserPw(hashedPasswd);
		
		accountDao.updateUserPw(userId, hashedPasswd);
	}
	
	// Application에 저장될 유저 장바구니 목록
	@Override
	public ArrayList<CartDto> getUserCart(String userId) {
		CartDao cartDao = new CartDao();
		ArrayList<CartDto> userCartArr = cartDao.selectUserCart(userId);
		return userCartArr;
	}
	public void deleteUser(String userId) {
		
		accountDao.updateUser(userId);
        
		
	}


}