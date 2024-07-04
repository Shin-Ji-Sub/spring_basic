package user.coffeeorderproject.service;

import java.util.ArrayList;
import java.util.List;

import user.coffeeorderproject.dao.BoardDao;
import user.coffeeorderproject.dto.BoardAttachDto;
import user.coffeeorderproject.dto.BoardCommentDto;
import user.coffeeorderproject.dto.BoardDto;

public class BoardService2 {

	BoardDao boardDao = new BoardDao();

	public void writeBoard(BoardDto board) {

		boardDao.insertBoard(board); // board 테이블에 데이터 저장 -> boardNo 결정 (DB에서)
		// board.getBoardNo() : 새로 만든 글 번호

		for (BoardAttachDto attach : board.getAttachments()) {
			attach.setBoardNo(board.getBoardNo()); // 위 게시글 insert 후 생성된 글번호 저장
			boardDao.insertBoardAttach(attach); // boardattach 테이블에 데이터 저장
		}

	}

	public List<BoardDto> findReviewBoard() {

		List<BoardDto> board = boardDao.selectReviewBoard();
		return board;

	}
	
	public List<BoardDto> findReviewBoardByRange(int start, int count) {

		List<BoardDto> board = boardDao.selectReviewBoardByRange(start, count);
		return board;

	}


	public BoardDto findBoardByBoardNo(int boardNo) {

		// 게시글 조회
		BoardDto board = boardDao.selectBoardByBoardNo(boardNo);

		// 첨부파일 조회
		
		ArrayList<BoardAttachDto> attaches = boardDao.selectBoardAttachByBoardNo(boardNo);
		board.setAttachments(attaches);
		
		ArrayList<BoardCommentDto> comments = boardDao.selectBoardCommentByBoardNo(boardNo);
		board.setComments(comments);

		return board;

	}

	public void modifyBoard(BoardDto board) {

		boardDao.updateBoard(board);

		for (BoardAttachDto attach : board.getAttachments()) {
			boardDao.insertBoardAttach(attach);
		}

	}

	public void deleteBoard(int boardNo) {
		boardDao.updateBoardDeleted(boardNo);

	}

	public BoardAttachDto findBoardAttachByAttachNo(int attachNo) {
		BoardAttachDto attach = boardDao.selectBoardAttachByAttachNo(attachNo);
		return attach;
	}

	public List<BoardDto> findAnnouncementBoard() {
		List<BoardDto> board = boardDao.selectAnnouncementBoard();
		return board;

	}

	public List<BoardDto> findInquiry1on1Board() {
		List<BoardDto> board = boardDao.selectInquiry1on1Board();
		return board;

	}

	public void writeComment(BoardCommentDto comment) {

		boardDao.insertBoardComment(comment);

	}

	public void deleteComment(int commentNo) {

		boardDao.deleteComment(commentNo);

	}

	public void editComment(BoardCommentDto comment) {

		boardDao.updateComment(comment);

	}

	public void writeReComment(BoardCommentDto comment) {
		// 부모 댓글을 조회해서 자식 댓글(대댓글)의 step, depth를 설정
		BoardCommentDto parent = boardDao.selectBoardCommentByCommentNo(comment.getCommentNo());
		comment.setGroupno(parent.getGroupno());
		comment.setReplycount(parent.getReplycount() + 1);
		comment.setReplylocation(parent.getReplylocation() + 1);

		// 새로 삽입될 대댓글보다 순서번호(step)가 뒤에 있는 대댓글의 step 수정 (+1)
		boardDao.updateStep(parent);

		boardDao.insertReComment(comment);
	}

	public List<BoardDto> findSearchReviewBoard(BoardDto board) {
	
		List<BoardDto> searchreviewlist = boardDao.selectSearchBoardList(board);
		
		return searchreviewlist;
	}

	public int getBoardCount() {
		return boardDao.selectBoardCount();
	}

	/*
	 * public List<BoardDto> findBoradByProdNo(int prodNo) { return
	 * boardDao.selectBoardByProdNo(prodNo); }
	 */


}
