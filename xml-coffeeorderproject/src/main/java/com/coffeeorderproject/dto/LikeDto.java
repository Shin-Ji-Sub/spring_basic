package com.coffeeorderproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class LikeDto {

	private int prodNo;
	private String userId;
	private int cartQuantity;
	private Date selectDate;
	private boolean likeChecked;
	
	
	private int productCategoryId;
	private String prodName;
	private int prodPrice;
	private String prodexplain;
	private Boolean prodActive;
	private String productCategoryName;
	
	
	
}