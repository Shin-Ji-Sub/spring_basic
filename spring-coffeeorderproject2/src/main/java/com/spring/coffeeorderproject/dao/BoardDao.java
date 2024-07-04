package user.coffeeorderproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import admin.coffeeorderproject.dto.OrdersDto;
import admin.coffeeorderproject.dto.ProductDto;
import user.coffeeorderproject.dto.BoardAttachDto;
import user.coffeeorderproject.dto.BoardCategoryDto;
import user.coffeeorderproject.dto.BoardCommentDto;
import user.coffeeorderproject.dto.BoardDto;

public class BoardDao {

	// insertProductNoAndBoard
	public void insertBoard(BoardDto board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 트랜잭션 시작
			conn.setAutoCommit(false);

			// 3. 명령 객체 만들기 - board 테이블에 데이터 삽입
			String sql = "INSERT INTO board (userid, boardtitle, boardcontent, boardcategoryid) VALUES (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, board.getUserId());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setInt(4, board.getBoardcategoryId());

			// 4. 명령 실행
			pstmt.executeUpdate();

			// 아타치 dao에 보드넘버를 넘겨주려면 필요한 작업
			rs = pstmt.getGeneratedKeys();

			// 5. 결과가 있으면 결과 처리
			int boardNo = 0;
			if (rs.next()) {
				boardNo = rs.getInt(1);
			}
			board.setBoardNo(boardNo);
			rs.close();
			pstmt.close();

			// 6. productreview 테이블에 데이터 삽입
			sql = "INSERT INTO productreview (boardno, prodno) VALUES (?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			pstmt.setInt(2, board.getProdNo());
			pstmt.executeUpdate();
			pstmt.close();

			// 트랜잭션 커밋
			conn.commit();

		} catch (Exception ex) {
			// 트랜잭션 롤백
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (Exception rollbackEx) {
				rollbackEx.printStackTrace();
			}
			ex.printStackTrace();
		} finally {
			// 연결 종료
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void insertBoardContnet(BoardDto board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. 명령 객체 만들기
			String sql = "INSERT INTO board (userid, boardtitle, boardcontent, boardcategoryid) VALUES (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getUserId());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setInt(4, board.getBoardcategoryId());

			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			pstmt.executeUpdate(); // insert, update, delete sql은 executeUpdate로 실행
			pstmt.close();
			// 5. 결과가 있으면 결과 처리
			sql = "SELECT LAST_INSERT_ID()"; // 현재 세션에서 발생한 마지막 AUTO_INCREMENT 값 조회
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			// 5. 결과가 있으면 결과 처리
			rs.next();
			int boardNo = rs.getInt(1);
			board.setBoardNo(boardNo);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
	}

	/*
	 * String sql =
	 * "SELECT b.boardno, b.userid, b.boardtitle, b.boarddate, b.boardmodifydate, b.boardcontent, b.boarddelete , p.prodname "
	 * + "FROM productreview pr " + "LEFT JOIN board b ON b.boardno = pr.boardno  "
	 * + "LEFT JOIN product p ON p.prodno = pr.prodno " +
	 * "WHERE boardcategoryid = 1 " + "ORDER BY boardno DESC ";
	 */
	// selectProductReviewBoard
	public List<BoardDto> selectReviewBoard() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> boardList = new ArrayList<>();

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. 명령 객체 만들기
			String sql = "SELECT boardno, userid, boardtitle, boarddate, boardmodifydate, boardcontent, boarddelete  "
					+ "FROM board " + "WHERE boardcategoryid = 1 " + "ORDER BY boardno DESC ";
			pstmt = conn.prepareStatement(sql);

			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			rs = pstmt.executeQuery(); // select sql은 executeQuery로 실행

			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				BoardDto board = new BoardDto();
				board.setBoardNo(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setBoardTitle(rs.getString(3));
				board.setBoardDate(rs.getDate(4));
				board.setBoardModifyDate(rs.getTimestamp(5));
				board.setBoardContent(rs.getString(6));
				board.setBoardDelete(rs.getBoolean(7));
				board.setProdName(rs.getString(8));
				boardList.add(board);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardList;
	}

	public List<BoardDto> selectReviewBoardByRange(int start, int count) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> boardList = new ArrayList<>();

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. 명령 객체 만들기
			String sql = "SELECT b.boardno, b.userid, b.boardtitle, b.boarddate, b.boardmodifydate, b.boardcontent, p.prodname "
					+ "FROM productreview pr " + "LEFT JOIN board b ON b.boardno = pr.boardno  "
					+ "LEFT JOIN product p ON p.prodno = pr.prodno "
					+ "WHERE boardcategoryid = 1 AND b.boarddelete = false " + "ORDER BY boardno DESC " + "LIMIT ?, ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);

			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			rs = pstmt.executeQuery(); // select sql은 executeQuery로 실행

			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				BoardDto board = new BoardDto();
				board.setBoardNo(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setBoardTitle(rs.getString(3));
				board.setBoardDate(rs.getDate(4));
				board.setBoardModifyDate(rs.getTimestamp(5));
				board.setBoardContent(rs.getString(6));
				board.setProdName(rs.getString(7));
				boardList.add(board);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardList;
	}

	public List<BoardDto> selectReviewBoardByRange2(int start, int count, String userId) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> boardList = new ArrayList<>();

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. 명령 객체 만들기
			String sql = "SELECT boardno, userid, boardcategoryid, boardtitle, boarddate, boardmodifydate, boardcontent, boarddelete "
		            + "FROM board "
		            + "WHERE boardcategoryid = 3 AND userid = ? AND boarddelete = false "
		            + "ORDER BY boardno DESC "
		            + "LIMIT ?, ?";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);
		
			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			rs = pstmt.executeQuery(); // select sql은 executeQuery로 실행

			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				BoardDto board = new BoardDto();
				board.setBoardNo(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setBoardcategoryId(rs.getInt(3));
				board.setBoardTitle(rs.getString(4));
				board.setBoardDate(rs.getDate(5));
				board.setBoardModifyDate(rs.getTimestamp(6));
				board.setBoardContent(rs.getString(7));
				board.setBoardDelete(rs.getBoolean(8));
				boardList.add(board);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardList;
	}

	public List<BoardDto> selectReviewBoardByRange3(int start, int count) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> boardList = new ArrayList<>();

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. 명령 객체 만들기
			String sql = "SELECT boardno, userid, boardcategoryid , boardtitle, boarddate, boardmodifydate, boardcontent, boarddelete "
					+ "FROM board " + " WHERE boardcategoryid = 2 AND boarddelete = false " + " ORDER BY boardno DESC " + " LIMIT ?, ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);

			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			rs = pstmt.executeQuery(); // select sql은 executeQuery로 실행

			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				BoardDto board = new BoardDto();
				board.setBoardNo(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setBoardcategoryId(rs.getInt(3));
				board.setBoardTitle(rs.getString(4));
				board.setBoardDate(rs.getDate(5));
				board.setBoardModifyDate(rs.getTimestamp(6));
				board.setBoardContent(rs.getString(7));
				board.setBoardDelete(rs.getBoolean(8));
				boardList.add(board);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardList;
	}

	public void insertBoardAttach(BoardAttachDto attach) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. 명령 객체 만들기
			String sql = "INSERT INTO boardattach (boardno, userfilename, savedfilename) VALUES (?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, attach.getBoardNo());
			pstmt.setString(2, attach.getUserfilename());
			pstmt.setString(3, attach.getSavedfilename());

			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			pstmt.executeUpdate(); // insert, update, delete sql은 executeUpdate로 실행

			// 5. 결과가 있으면 결과 처리

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
	}

	public BoardDto selectBoardByBoardNo(int boardNo) {

		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		BoardDto board = null; // 조회한 데이터를 저장할 DTO 객체

		try {
			// 1. Driver 등록
			// DriverManager.registerDriver(new Driver());
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT b.boardno, b.userid, b.boardtitle, b.boarddate, b.boardmodifydate, b.boardcontent, p.prodname "
					+ "FROM productreview pr " + "LEFT JOIN board b ON b.boardno = pr.boardno  "
					+ "LEFT JOIN product p ON p.prodno = pr.prodno " + "WHERE boardcategoryid = 1 ANd b.boardno = ? "
					+ "ORDER BY boardno DESC ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			while (rs.next()) { // 결과 집합의 다음 행으로 이동
				board = new BoardDto();
				board.setBoardNo(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setBoardTitle(rs.getString(3));
				board.setBoardDate(rs.getDate(4));
				board.setBoardModifyDate(rs.getTimestamp(5));
				board.setBoardContent(rs.getString(6));
				board.setProdName(rs.getString(7));

			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return board;
	}

	// edit 서블릿 사용 DAO
	public BoardDto selectBoardByBoardNo2(int boardNo) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		BoardDto board = null; // 조회한 데이터를 저장할 DTO 객체

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT boardno, userid, boardtitle, boarddate, boardmodifydate, boardcontent " + "FROM board "
					+ "WHERE boardno = ? " + "ORDER BY boardno DESC ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			if (rs.next()) { // 결과 집합의 다음 행으로 이동
				board = new BoardDto();
				board.setBoardNo(rs.getInt("boardno"));
				board.setUserId(rs.getString("userid"));
				board.setBoardTitle(rs.getString("boardtitle"));
				board.setBoardDate(rs.getDate("boarddate"));
				board.setBoardModifyDate(rs.getTimestamp("boardmodifydate"));
				board.setBoardContent(rs.getString("boardcontent"));
			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return board;
	}

	public ArrayList<BoardAttachDto> selectBoardAttachByBoardNo(int boardNo) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		ArrayList<BoardAttachDto> attachments = new ArrayList<>();

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT fileno, boardno, userfilename, savedfilename " + "FROM boardattach "
					+ "WHERE boardno = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			while (rs.next()) { // 결과 집합의 다음 행으로 이동
				BoardAttachDto attach = new BoardAttachDto();
				attach.setFileNo(rs.getInt(1));
				attach.setBoardNo(rs.getInt(2));
				attach.setUserfilename(rs.getString(3));
				attach.setSavedfilename(rs.getString(4));

				attachments.add(attach);
			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return attachments;
	}

	public void updateBoard(BoardDto board) {

		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "UPDATE board SET boardtitle = ?, boardcontent = ? WHERE boardno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setInt(3, board.getBoardNo());

			// 4. 명령 실행
			pstmt.executeUpdate();

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

	}

	public void updateBoardDeleted(int boardNo) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "UPDATE board SET boarddelete = TRUE WHERE boardno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			// 4. 명령 실행
			pstmt.executeUpdate();

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

	}

	public BoardAttachDto selectBoardAttachByAttachNo(int attachNo) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		BoardAttachDto attach = null;

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT fileno, boardno, userfilename, savedfilename " + "FROM boardattach "
					+ "WHERE fileno = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, attachNo);

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			if (rs.next()) { // 결과 집합의 다음 행으로 이동
				attach = new BoardAttachDto();
				attach.setFileNo(rs.getInt(1));
				attach.setBoardNo(rs.getInt(2));
				attach.setUserfilename(rs.getString(3));
				attach.setSavedfilename(rs.getString(4));
			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return attach;
	}

	public void insertBoardCategory(BoardCategoryDto boardCategory) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. 명령 객체 만들기
			String sql = "INSERT INTO boardattach (boardcategoryid, boardcategoryname ) VALUES (?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardCategory.getBoardCateGoryid());
			pstmt.setString(2, boardCategory.getBoardCateGoryname());

			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			pstmt.executeUpdate(); // insert, update, delete sql은 executeUpdate로 실행

			// 5. 결과가 있으면 결과 처리

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
	}

	public List<BoardDto> selectAnnouncementBoard() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> boardList = new ArrayList<>();

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. 명령 객체 만들기
			String sql = "SELECT boardno, userid, boardtitle, boarddate, boardmodifydate, boardcontent, boarddelete "
					+ "FROM board " + " WHERE boardcategoryid = 2 " + "ORDER BY boardno DESC ";
			pstmt = conn.prepareStatement(sql);

			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			rs = pstmt.executeQuery(); // select sql은 executeQuery로 실행

			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				BoardDto board = new BoardDto();
				board.setBoardNo(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setBoardTitle(rs.getString(3));
				board.setBoardDate(rs.getDate(4));
				board.setBoardModifyDate(rs.getTimestamp(5));
				board.setBoardContent(rs.getString(6));
				board.setBoardDelete(rs.getBoolean(7));
				boardList.add(board);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardList;
	}

	public List<BoardDto> selectInquiry1on1Board() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> boardList = new ArrayList<>();

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. 명령 객체 만들기
			String sql = "SELECT boardno, userid, boardtitle, boarddate, boardmodifydate, boardcontent, boarddelete "
					+ "FROM board " + " WHERE boardcategoryid = 3 " + "ORDER BY boardno DESC ";
			pstmt = conn.prepareStatement(sql);

			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			rs = pstmt.executeQuery(); // select sql은 executeQuery로 실행

			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				BoardDto board = new BoardDto();
				board.setBoardNo(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setBoardTitle(rs.getString(3));
				board.setBoardDate(rs.getDate(4));
				board.setBoardModifyDate(rs.getTimestamp(5));
				board.setBoardContent(rs.getString(6));
				board.setBoardDelete(rs.getBoolean(7));
				boardList.add(board);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardList;
	}

	public void insertBoardComment(BoardCommentDto comment) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "INSERT INTO comment (boardno, userid, commentcontent, groupno, replycount, replylocation) VALUES (?, ?, ?, -1, 1, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getBoardNo());
			pstmt.setString(2, comment.getUserId());
			pstmt.setString(3, comment.getCommentContent());

			// 4-1. 명령 실행 1 -> 댓글 삽입
			pstmt.executeUpdate();
			pstmt.close();

			// 4-2. 명령 실행 2 -> groupno 변경 (최상위 글의 글번호를 groupno로 사용)
			sql = "UPDATE comment SET groupno = LAST_INSERT_ID() where commentno = LAST_INSERT_ID()";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

	}

	public BoardCommentDto selectBoardCommentByCommentNo(int commentNo) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		BoardCommentDto comment = null;

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT commentno, userid, boardno, commentcontent, commentdate, commodifydate, commentactive, groupno, replycount, replylocation "
					+ "FROM comment " + "WHERE commentno = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentNo);

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			while (rs.next()) { // 결과 집합의 다음 행으로 이동
				comment = new BoardCommentDto();
				comment.setCommentNo(rs.getInt(1));
				comment.setUserId(rs.getString(2));
				comment.setBoardNo(rs.getInt(3));
				comment.setCommentContent(rs.getString(4));
				comment.setCommentDate(rs.getTimestamp(5));
				comment.setComModifyDate(rs.getTimestamp(6));
				comment.setCommentActive(rs.getBoolean(7));
				comment.setGroupno(rs.getInt(8));
				comment.setReplycount(rs.getInt(9));
				comment.setReplylocation(rs.getInt(10));
			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return comment;
	}

	public ArrayList<BoardCommentDto> selectBoardCommentByBoardNo(int boardNo) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		ArrayList<BoardCommentDto> comments = new ArrayList<>();

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT commentno, userid, boardno,  commentcontent, commentdate, commodifydate, commentactive, groupno, replycount, replylocation "
					+ "FROM comment " + "WHERE boardno = ? " + "ORDER BY groupno DESC, replycount ASC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			while (rs.next()) { // 결과 집합의 다음 행으로 이동
				BoardCommentDto comment = new BoardCommentDto();
				comment.setCommentNo(rs.getInt(1));
				comment.setUserId(rs.getString(2));
				comment.setBoardNo(rs.getInt(3));
				comment.setCommentContent(rs.getString(4));
				comment.setCommentDate(rs.getTimestamp(5));
				comment.setComModifyDate(rs.getTimestamp(6));
				comment.setCommentActive(rs.getBoolean(7));
				comment.setGroupno(rs.getInt(8));
				comment.setReplycount(rs.getInt(9));
				comment.setReplylocation(rs.getInt(10));

				comments.add(comment);
			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return comments;
	}

	public void deleteComment(int commentNo) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "UPDATE comment set commentactive = true WHERE commentno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentNo);

			// 4. 명령 실행
			pstmt.executeUpdate();

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

	}

	public void updateStep(BoardCommentDto parent) {
		// TODO Auto-generated method stub

		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "UPDATE comment SET replycount = replycount + 1 WHERE groupno = ? AND replycount > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parent.getGroupno());
			pstmt.setInt(2, parent.getReplycount());

			// 4-1. 명령 실행
			pstmt.executeUpdate();

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

	}

	public void insertReComment(BoardCommentDto comment) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "INSERT INTO comment (userid, boardno, commentcontent, groupno, replycount, replylocation) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getUserId());
			pstmt.setInt(2, comment.getBoardNo());
			pstmt.setString(3, comment.getCommentContent());
			pstmt.setInt(4, comment.getGroupno());
			pstmt.setInt(5, comment.getReplycount());
			pstmt.setInt(6, comment.getReplylocation());

			// 4-1. 명령 실행
			pstmt.executeUpdate();

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
	}

	public void updateComment(BoardCommentDto comment) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "UPDATE comment set commentcontent = ? WHERE commentno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getCommentContent());
			pstmt.setInt(2, comment.getCommentNo());

			// 4. 명령 실행
			pstmt.executeUpdate();

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

	}

	public List<BoardDto> selectSearchBoardList(BoardDto board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<BoardDto> boards = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			String sql = "SELECT b.boardno, b.userid, b.boardtitle, b.boarddate, b.boardmodifydate, b.boardcontent, p.prodname "
					+ "FROM productreview pr " + "LEFT JOIN board b ON b.boardno = pr.boardno "
					+ "LEFT JOIN product p ON p.prodno = pr.prodno "
					+ "WHERE (p.prodname LIKE ? OR (b.boardcategoryid = 1 AND (b.userid LIKE ? OR b.boardtitle LIKE ?))) "
					+ "ORDER BY b.boardno DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + board.getUserId() + "%");
			pstmt.setString(2, "%" + board.getBoardTitle() + "%");
			pstmt.setString(3, "%" + board.getProdName() + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDto resultBoard = new BoardDto();
				resultBoard.setBoardNo(rs.getInt(1));
				resultBoard.setUserId(rs.getString(2));
				resultBoard.setBoardTitle(rs.getString(3));
				resultBoard.setBoardDate(rs.getDate(4));
				resultBoard.setBoardModifyDate(rs.getTimestamp(5));
				resultBoard.setBoardContent(rs.getString(6));
				resultBoard.setProdName(rs.getString(7));

				boards.add(resultBoard);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boards;
	}

	public int selectBoardCount() {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		int boardCount = 0;

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT COUNT(*) FROM board WHERE boarddelete = false AND boardcategoryid = 1  ";
			pstmt = conn.prepareStatement(sql);

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			if (rs.next()) { // 결과 집합의 다음 행으로 이동
				boardCount = rs.getInt(1);

			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardCount;
	}
	
	public int selectBoardAnnoCount() {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		int boardCount = 0;

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT COUNT(*) FROM board WHERE boarddelete = false AND boardcategoryid = 2  ";
			pstmt = conn.prepareStatement(sql);

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			if (rs.next()) { // 결과 집합의 다음 행으로 이동
				boardCount = rs.getInt(1);

			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardCount;
	}
	
	public int selectBoard1on1Count() {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		int boardCount = 0;

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT COUNT(*) FROM board WHERE boarddelete = false AND boardcategoryid = 3  ";
			pstmt = conn.prepareStatement(sql);

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			if (rs.next()) { // 결과 집합의 다음 행으로 이동
				boardCount = rs.getInt(1);

			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardCount;
	}
	
	public int selectBoardCountByProdNo(int prodNo) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		int boardCount = 0;

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT COUNT(*) " 
					+ "FROM productreview pr " 
					+ "LEFT JOIN board b ON b.boardno = pr.boardno  "
					+ "LEFT JOIN product p ON p.prodno = pr.prodno  "
					+ "WHERE b.boardcategoryid = 1 AND pr.prodno = ? AND b.boarddelete = false";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prodNo);

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			if (rs.next()) { // 결과 집합의 다음 행으로 이동
				boardCount = rs.getInt(1);

			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardCount;
	}

	
	public int selectBoardCountByKeyword(String keyword) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		int boardCount = 0;

		try {
			// 1. Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT COUNT(*)  "
					+ "FROM productreview pr " + "LEFT JOIN board b ON b.boardno = pr.boardno "
					+ "LEFT JOIN product p ON p.prodno = pr.prodno "
					+ "WHERE (p.prodname LIKE ? OR (b.boardcategoryid = 1 AND (b.userid LIKE ? OR b.boardtitle LIKE ?))) "
					+ "ORDER BY b.boardno DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			if (rs.next()) { // 결과 집합의 다음 행으로 이동
				boardCount = rs.getInt(1);
				boardCount = rs.getInt(1);
				boardCount = rs.getInt(1);

			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardCount;
	}


	
	public List<BoardDto> selectBoardByProdNo(int prodNo, int start, int count) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<BoardDto> prods = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			String sql = "SELECT b.boardno, b.userid, b.boardtitle, b.boarddate, b.boardmodifydate, b.boardcontent, p.prodname, p.prodno "
					+ "FROM productreview pr " + "LEFT JOIN board b ON b.boardno = pr.boardno  "
					+ "LEFT JOIN product p ON p.prodno = pr.prodno "
					+ "WHERE b.boardcategoryid = 1 AND pr.prodno = ? AND b.boarddelete = false "
					+ "ORDER BY b.boardno DESC " + "LIMIT ?, ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prodNo);
			pstmt.setInt(2, start);
			pstmt.setInt(3, count);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDto resultBoard = new BoardDto();
				resultBoard.setBoardNo(rs.getInt(1));
				resultBoard.setUserId(rs.getString(2));
				resultBoard.setBoardTitle(rs.getString(3));
				resultBoard.setBoardDate(rs.getDate(4));
				resultBoard.setBoardModifyDate(rs.getTimestamp(5));
				resultBoard.setBoardContent(rs.getString(6));
				resultBoard.setProdName(rs.getString(7));
				resultBoard.setProdNo(rs.getInt(8));
				prods.add(resultBoard);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return prods;
	}
	
	
	public List<BoardDto> selectBoardByKeyword(String keyword, int start, int count) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<BoardDto> prods = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			String sql = "SELECT b.boardno, b.userid, b.boardtitle, b.boarddate, b.boardmodifydate, b.boardcontent, p.prodname, p.prodno "
					+ "FROM productreview pr " + "LEFT JOIN board b ON b.boardno = pr.boardno "
					+ "LEFT JOIN product p ON p.prodno = pr.prodno "
					+ "WHERE ( (b.boardcategoryid = 1 AND ( p.prodname LIKE ?  OR b.userid LIKE ?  OR b.boardtitle LIKE ? )))  "
					+ "ORDER BY b.boardno DESC "
					+ "LIMIT ?, ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setInt(4, start);
			pstmt.setInt(5, count);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDto resultBoard = new BoardDto();
				resultBoard.setBoardNo(rs.getInt(1));
				resultBoard.setUserId(rs.getString(2));
				resultBoard.setBoardTitle(rs.getString(3));
				resultBoard.setBoardDate(rs.getDate(4));
				resultBoard.setBoardModifyDate(rs.getTimestamp(5));
				resultBoard.setBoardContent(rs.getString(6));
				resultBoard.setProdName(rs.getString(7));
				resultBoard.setProdNo(rs.getInt(8));
				prods.add(resultBoard);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return prods;
	}


	public List<ProductDto> selectProdByProdNoByProdName() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<ProductDto> prods = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			String sql = "SELECT * " + "FROM product " + "WHERE prodactive = false " + " ORDER BY prodno DESC ";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDto resultProduct = new ProductDto();
				resultProduct.setProdNo(rs.getInt(1));
				resultProduct.setProductCategoryId(rs.getInt(2));
				resultProduct.setProdName(rs.getString(3));
				resultProduct.setProdPrice(rs.getInt(4));
				resultProduct.setProdexplain(rs.getString(5));
				resultProduct.setProdActive(rs.getBoolean(6));
				prods.add(resultProduct);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return prods;
	}

	public BoardDto selectBoardContentByBoardNo(int boardNo) {
		Connection conn = null; // 연결과 관련된 JDBC 호출 규격 ( 인터페이스 )
		PreparedStatement pstmt = null; // 명령 실행과 관련된 JDBC 호출 규격 ( 인터페이스 )
		ResultSet rs = null; // 결과 처리와 관련된 JDBC 호출 규격 ( 인터페이스 )

		BoardDto board = null; // 조회한 데이터를 저장할 DTO 객체

		try {
			// 1. Driver 등록
			// DriverManager.registerDriver(new Driver());
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 및 연결 객체 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. SQL 작성 + 명령 객체 가져오기
			String sql = "SELECT boardno, userid, boardtitle, boarddate, boardmodifydate, boardcontent " + "FROM board "
					+ "WHERE boardno = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			// 4. 명령 실행
			rs = pstmt.executeQuery(); // executeQuery : select 일 때 사용하는 메서드

			// 5. 결과 처리 (결과가 있다면 - SELECT 명령을 실행한 경우)
			while (rs.next()) { // 결과 집합의 다음 행으로 이동
				board = new BoardDto();
				board.setBoardNo(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setBoardTitle(rs.getString(3));
				board.setBoardDate(rs.getDate(4));
				board.setBoardModifyDate(rs.getTimestamp(5));
				board.setBoardContent(rs.getString(6));

			}

		} catch (Exception ex) {
			ex.printStackTrace(); // 개발 용도로 사용
		} finally {
			// 6. 연결 닫기
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return board;
	}

	public BoardDto selectBoardByProdName(int prodNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		BoardDto board = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			String sql = "SELECT  prodname, prodno, prodexplain " 
					+ "FROM product "
					+ "WHERE prodno = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prodNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				board = new BoardDto();
				board.setProdName(rs.getString(1));
				board.setProdNo(rs.getInt(2));
				board.setProdExplain(rs.getString(3));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return board;
	}

	public OrdersDto selectUesridByOrders(int orderId) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		OrdersDto orders = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			String sql = "SELECT p.prodname, p.prodno " + "FROM orderdetail od  "
					+ "LEFT JOIN orders  o  ON  o.orderid =  od.orderid "
					+ "LEFT JOIN product p  ON  p.prodno   =  od.prodno " + "WHERE od.orderid = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orders = new OrdersDto();
				orders.setProdName(rs.getString(1));
				orders.setProdNo(rs.getInt(2));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return orders;
	}

	public BoardDto selectUserIdByUseradmin(String userId) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		BoardDto user = null;

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. 명령 객체 만들기
			String sql = "SELECT  b.userid, u.useradmin  "
					+ " FROM user " 
					+ " WHERE  userid = ? AND b.boarddelete = false "  ;
			
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
		
		
			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			rs = pstmt.executeQuery(); // select sql은 executeQuery로 실행

			// 5. 결과가 있으면 결과 처리
			if (rs.next()) {
				BoardDto board = new BoardDto();
				board.setBoardNo(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setBoardcategoryId(rs.getInt(3));
				board.setBoardTitle(rs.getString(4));
				board.setBoardDate(rs.getDate(5));
				board.setBoardModifyDate(rs.getTimestamp(6));
				board.setBoardContent(rs.getString(7));
				board.setBoardDelete(rs.getBoolean(8));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return user;
	}

	public List<BoardDto> selectReviewBoardAdminByRange(int start, int count) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> boardList = new ArrayList<>();

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			// 3. 명령 객체 만들기
			String sql = "SELECT boardno, userid, boardcategoryid, boardtitle, boarddate, boardmodifydate, boardcontent, boarddelete "
		            + "FROM board "
		            + "WHERE boardcategoryid = 3  AND boarddelete = false "
		            + "ORDER BY boardno DESC "
		            + "LIMIT ?, ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);
		
			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			rs = pstmt.executeQuery(); // select sql은 executeQuery로 실행

			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				BoardDto board = new BoardDto();
				board.setBoardNo(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setBoardcategoryId(rs.getInt(3));
				board.setBoardTitle(rs.getString(4));
				board.setBoardDate(rs.getDate(5));
				board.setBoardModifyDate(rs.getTimestamp(6));
				board.setBoardContent(rs.getString(7));
				board.setBoardDelete(rs.getBoolean(8));
				boardList.add(board);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}

		return boardList;
	}

	
	
}
