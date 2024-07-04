package user.coffeeorderproject.service;

import java.util.ArrayList;

import user.coffeeorderproject.dao.LikeDao;
import user.coffeeorderproject.dto.LikeDto;

public class LikeService {
	
	private LikeDao likeDao = new LikeDao();

	
	public boolean addLike(int prodNo, String userId) {
        try {
            likeDao.addLike(prodNo, userId);
            return true;  // 성공 시 true 반환
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 실패 시 false 반환
        }
    }

    public boolean removeLike(int prodNo, String userId) {
        try {
            likeDao.removeLike(prodNo, userId);
            return true;  // 성공 시 true 반환
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 실패 시 false 반환
        }
    }
    
    public ArrayList<LikeDto> findLikeByUserId(String userId) {
		
		ArrayList<LikeDto> like = likeDao.getLikeList(userId);
		return like;
		
	}
    
    public ArrayList<LikeDto> findLikePageByUserId(int pageNum, String userId) {
		
		ArrayList<LikeDto> like = likeDao.getLikeListPage(pageNum, userId);
		return like;
		
	}

	public int getUserLikeCount(String userId) {
		int likeCount = likeDao.getLikeCount(userId);
		return likeCount;
	}
	
}
