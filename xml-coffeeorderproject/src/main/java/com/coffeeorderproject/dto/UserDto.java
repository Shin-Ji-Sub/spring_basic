package com.coffeeorderproject.dto;

import java.sql.Date;
import java.util.ArrayList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDto {
	
	@NotBlank(message = "아이디를 입력하세요")
	@Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$", message = "1형식이 맞지 않습니다")
	private String userId;
	
	@NotBlank(message = "이름을 입력하세요")
	private String userName;
	
	@NotBlank(message = "닉네임를 입력하세요")
	private String userNickname;
	
	@NotBlank(message = "핸드폰 번호를 입력하세요")
	@Pattern(regexp = "^\\d{3}\\d{3,4}\\d{4}$", message = "형식이 맞지 않습니다")
	private String userPhone;
	
	@NotBlank(message = "이메일을 입력하세요")
	@Pattern(regexp = "^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]+$", message = "2형식이 맞지 않습니다")
	private String userEmail;
	
	@NotBlank(message = "비밀번호를 입력하세요")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\\\":{}|<>]).{8,}$", message = "3형식이 맞지 않습니다")
	private String userPw;
	
	private Boolean userAdmin;
	private Date userRegidate;
	private Boolean userActive;
	
	
	private int couponId;
	private ArrayList<UserCouponDto> usercoupon;
	
//	public ArrayList<UserCouponDto> getUsercoupon() {
//		return usercoupon;
//	}
//	public void setUsercoupon(ArrayList<UserCouponDto> usercoupon) {
//		this.usercoupon = usercoupon;
//	}

	
	
	
	
}
