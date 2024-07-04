package com.coffeeorderproject.service;

import java.util.ArrayList;

import admin.coffeeorderproject.dto.OrdersDto;
import admin.coffeeorderproject.dto.ProductDto;
import admin.coffeeorderproject.service.SearchService;
import user.coffeeorderproject.dao.CartDao;
import user.coffeeorderproject.dao.PaymentDao;

public class PaymentOrderService {
	
	private PaymentDao payment = new PaymentDao();
	
	public ArrayList<ProductDto> findOrderByProductItem(String text) {
		ArrayList<ProductDto> orderProduct = payment.selectOrderProduct(text);
		return orderProduct;
	}

	public void modifyCartQuantity(String[] prodNoArr, String[] cartQuantityArr, String userId) {
		
		for (int i = 0; i < prodNoArr.length; i++) {
			int prodNo = Integer.parseInt(prodNoArr[i]);
			int cartQuantity = Integer.parseInt(cartQuantityArr[i]);
			payment.updateCartQuantity(prodNo, cartQuantity, userId);
		}
		
	}
	
	public void insertOrder2(String userId, OrdersDto orders, String couponuse) {
		CartDao cartDao = new CartDao();
		payment.insertOrder(orders, userId);
		
		if(couponuse.equals("true")) { // 쿠폰 사용했으면
			payment.deleteCoupon(userId); // 사용한 쿠폰 삭제
		}
		
		// 10번째 결제마다 쿠폰 지급 로직
		int count = payment.selectUserOrdersCount(userId);
		if(count > 0 && count % 10 == 9) {
			SearchService service = new SearchService();
			service.setUserCoupon(userId);
		}
	}
	
	public void insertOrder(String[] prodNo, String[] cartQuantity) {
		CartDao cartDao = new CartDao();
		
		for(int i=0; i< prodNo.length; i++) {
			int prodNoo = Integer.parseInt(prodNo[i]);
			int cartQuantityy = Integer.parseInt(cartQuantity[i]); 
			
			payment.insertOrderDetail(prodNoo, cartQuantityy);
			cartDao.deleteProduct(prodNoo); // 장바구니 비우기
		}
		
		
		
		
	}

	

	

}
