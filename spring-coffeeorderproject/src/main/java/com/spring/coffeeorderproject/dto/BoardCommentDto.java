package com.spring.coffeeorderproject.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardCommentDto {
	
	private int commentNo;
	private String userId;
	private int boardNo;
	private String commentContent;
	private Timestamp commentDate;
	private Timestamp comModifyDate;
	private boolean commentActive;
	

	private int groupno;
	private int replycount;
	private int replylocation;
	
	
	
	
	
}
