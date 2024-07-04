package com.coffeeorderproject.service;

import java.util.ArrayList;

import user.coffeeorderproject.dao.CartDao;
import user.coffeeorderproject.dto.CartDto;

public class CartService {

	private CartDao cartDao = new CartDao();
	
	public void putProductIntoCart(int productCount, int prodno, String userId) {
		
		cartDao.inputProductIntoCart(productCount, prodno, userId);
		
	}

	// 장바구니에 같은 아이디 같은 상품을 담은 이력이 있는지 확인
	public Boolean checkUserInCart(String userId, int prodno) {
		Boolean isUser = cartDao.selectIsUserInCart(userId, prodno);
		return isUser;
	}

	public void modifyProductCount(int productCount, int prodno, String userId) {
		
		cartDao.updateProductCount(productCount, prodno, userId);
		
	}

	public ArrayList<CartDto> getAllCartProduct(String userId) {
		ArrayList<CartDto> productArr = cartDao.selectAllCartProduct(userId);
		return productArr;
	}

	public void removeProduct(int prodNo) {
		cartDao.deleteProduct(prodNo);
		
	}

}
