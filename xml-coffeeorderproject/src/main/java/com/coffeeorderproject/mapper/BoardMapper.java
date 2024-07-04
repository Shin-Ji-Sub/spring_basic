package com.coffeeorderproject.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coffeeorderproject.dto.BoardAttachDto;
import com.coffeeorderproject.dto.BoardDto;
import com.coffeeorderproject.dto.ProductDto;

@Mapper
public interface BoardMapper {

	void insertBoard(BoardDto board);

	void insertBoardAttach(BoardAttachDto attach);

	List<BoardDto> selectReviewBoardByRange(int start, int count);

	int selectReviewCount();

	List<ProductDto> selectProdByProdNoByProdName();

	BoardDto selectBoardByBoardNo(int boardNo);

	List<BoardAttachDto> selectBoardAttachByBoardNo(int boardNo);

	void insertReview(BoardDto board);

	BoardAttachDto selectBoardAttachByAttachNo(int attachNo);


	
}
