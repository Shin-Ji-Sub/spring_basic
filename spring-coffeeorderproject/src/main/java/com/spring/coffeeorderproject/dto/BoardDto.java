package com.spring.coffeeorderproject.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import admin.coffeeorderproject.dto.ProductDto;
import lombok.Data;

@Data
public class BoardDto {

	//board 게시판 정보
	private int boardNo;
	private String userId;
	private int boardcategoryId;
	private String boardTitle;
	private Date boardDate;
	private Timestamp boardModifyDate;
	private String boardContent;
	private boolean boardDelete;
	private String usernickname;

	// 유저가 선택한 상품 정보
	private int prodNo;
	private String prodName;
	private String prodExplain;
	
	private int userAdmin;

	private ArrayList<BoardCategoryDto> categorys;
	private List<BoardAttachDto> attachments;
	private ArrayList<BoardCommentDto> comments;
	private List<ProductDto> products;

	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public int getBoardNo() {
		return boardNo;

	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getBoardcategoryId() {
		return boardcategoryId;
	}

	public void setBoardcategoryId(int boardcategoryId) {
		this.boardcategoryId = boardcategoryId;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardDtitle) {
		this.boardTitle = boardDtitle;
	}

	public Date getBoardDate() {
		return boardDate;
	}

	public void setBoardDate(Date boardDate) {
		this.boardDate = boardDate;
	}

	public Timestamp getBoardModifyDate() {
		return boardModifyDate;
	}

	public void setBoardModifyDate(Timestamp boardModifyDate) {
		this.boardModifyDate = boardModifyDate;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public boolean isBoardDelete() {
		return boardDelete;
	}

	public void setBoardDelete(boolean boardDelete) {
		this.boardDelete = boardDelete;
	}
	
	public String getProdExplain() {
		return prodExplain;
	}

	public void setProdExplain(String prodExplain) {
		this.prodExplain = prodExplain;
	}

	public List<BoardAttachDto> getAttachments() {
		return attachments;
	}

	public int getUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(int userAdmin) {
		this.userAdmin = userAdmin;
	}

	public void setAttachments(List<BoardAttachDto> attchments) {
		this.attachments = attchments;
	}

	public ArrayList<BoardCategoryDto> getCategorys() {
		return categorys;
	}

	public void setCategorys(ArrayList<BoardCategoryDto> categorys) {
		this.categorys = categorys;
	}

	public ArrayList<BoardCommentDto> getComments() {
		return comments;
	}

	public void setComments(ArrayList<BoardCommentDto> comments) {
		this.comments = comments;
	}

	public List<ProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}

	public String getUsernickname() {
		return usernickname;
	}

	public void setUsernickname(String usernickname) {
		this.usernickname = usernickname;
	}

//	@Override
//	public String toString() {
//		return String.format("[%d][%s][%d][%s][%s][%s][%s][%b]",boardNo,userId,boardcategoryId,boardTitle,boardDate,boardModifyDate,boardContent,boardDelete);
//		

//	}

}
