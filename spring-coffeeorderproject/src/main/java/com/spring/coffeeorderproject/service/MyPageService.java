package user.coffeeorderproject.service;

import java.util.ArrayList;

import user.coffeeorderproject.common.Util;
import user.coffeeorderproject.dao.CouponDao;
import user.coffeeorderproject.dao.MyPageDao;
import user.coffeeorderproject.dao.UserBoardDao;
import user.coffeeorderproject.dto.BoardDto;
import user.coffeeorderproject.dto.UserCouponDto;
import user.coffeeorderproject.dto.UserDto;
import user.coffeeorderproject.dto.UserOrderDto;
import user.coffeeorderproject.dao.UserOrderDao;

public class MyPageService {
	
	MyPageDao myPageDao = new MyPageDao(); 
	UserOrderDao orderDao = new UserOrderDao();
	CouponDao couponDao = new CouponDao();
	UserBoardDao boardDao = new UserBoardDao();
	
//	비밀번호 암호화
	public void updateUserInfo(UserDto user) {
		String hashedPasswd = Util.getHashedString(user.getUserPw(), "SHA-256");
		user.setUserPw(hashedPasswd);
		
		myPageDao.updateUserInfo(user);		
	}
	
	public ArrayList<UserCouponDto> UserCoupon(String userId) {
		ArrayList<UserCouponDto> coupon = couponDao.selectUserCouponList(userId);
		return coupon;
	}

	public ArrayList<BoardDto> UserBoard(String userId) {
		ArrayList<BoardDto> userBoardList = boardDao.selectAllUserBoardList(userId);
		return userBoardList;
	}
	
	public ArrayList<UserOrderDto> findMyPageUserorder(String userId) {
		ArrayList<UserOrderDto> orderList = orderDao.selectMyPageUserOrders(userId);
		return orderList;	
	}
	
	public ArrayList<UserOrderDto> getUserOrder(int pageNum, String userId) {
		ArrayList<UserOrderDto> orderArr = orderDao.selectUserOrderList(pageNum,userId);
		return orderArr;
	}

	public int getUserOrderCount(String userId) {
		int orderCount = orderDao.getBoardCount(userId);
		return orderCount;
		
	}

	
}
