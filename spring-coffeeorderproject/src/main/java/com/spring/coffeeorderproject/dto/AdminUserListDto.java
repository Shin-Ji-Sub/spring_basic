package com.spring.coffeeorderproject.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class AdminUserListDto {
	private boolean userActive;
	private String userId;
	private String userName;
	private String userPhone;
	private Date userRegidate;
	private boolean userAdmin;
	private String userEmail;
	private String userNickName;
	
	private boolean iscoupon;
	private int buyCount;
	private int couponCount;
	
	
}
