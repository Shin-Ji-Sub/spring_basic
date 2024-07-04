package user.coffeeorderproject.service;

import java.util.ArrayList;

import user.coffeeorderproject.dao.UserBoardDao;
import user.coffeeorderproject.dto.BoardDto;

public class UserBoardService {
	
	private UserBoardDao userBoardDao = new UserBoardDao();
	
	public ArrayList<BoardDto> getUserBoard(int pageNum, String userId) {
		ArrayList<BoardDto> boardArr = userBoardDao.selectUserBoard(pageNum,userId);
		return boardArr;
	}

	public int getUserBoardCount(String userId) {
		int boardCount = userBoardDao.getBoardCount(userId);
		return boardCount;
	}

}
