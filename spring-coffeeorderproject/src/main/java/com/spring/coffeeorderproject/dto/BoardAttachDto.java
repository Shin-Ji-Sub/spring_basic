package com.spring.coffeeorderproject.dto;

import lombok.Data;

@Data
public class BoardAttachDto {

	private int fileNo;
	private int boardNo;
	private String userfilename;
	private String savedfilename;
	
	private BoardDto board;
	
	
	
	
	
}
