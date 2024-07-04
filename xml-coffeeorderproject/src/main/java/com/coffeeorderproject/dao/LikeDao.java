package com.coffeeorderproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import user.coffeeorderproject.dto.LikeDto;

public class LikeDao {
	
	public void removeLike(int prodNo, String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			String sql = "DELETE FROM hollys.like WHERE userid = ? AND prodno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, prodNo);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				pstmt.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public void addLike(int prodNo, String userId) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

			String sql = "INSERT INTO hollys.like (userid, prodno, cartquantity, likechecked) VALUES ( ?, ?, 0 , true)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, prodNo);
			

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try {
				pstmt.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}
	// 찜 목록 Dao
		public ArrayList<LikeDto> getLikeList(String userid) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			ArrayList<LikeDto> likeList = new ArrayList<>();
			try {
				// 1. 드라이버 준비
				Class.forName("com.mysql.cj.jdbc.Driver");

				// 2. 연결 객체 만들기
				conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

				String sql = "SELECT l.prodno, l.userid, l.selectdate, l.likechecked, p.prodName, p.prodPrice, p.prodexplain "
						+ "FROM `like` l " + "LEFT OUTER JOIN product p " + "ON l.prodno = p.prodno "
						+ "WHERE userid = ? AND likechecked = true ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					LikeDto like = new LikeDto();
					like.setProdNo(rs.getInt(1));
					like.setUserId(rs.getString(2));
					like.setSelectDate(rs.getDate(3));
					like.setLikeChecked(rs.getBoolean(4));
					like.setProdName(rs.getString(5));
					like.setProdPrice(rs.getInt(6));
					like.setProdexplain(rs.getString(7));
					likeList.add(like);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				// 6. 연결 종료
				try { rs.close(); } catch (Exception ex) {}
				try { pstmt.close(); } catch (Exception ex) {}
				try { conn.close(); } catch (Exception ex) {}
			}
			return likeList;
		}
		
		public ArrayList<LikeDto> getLikeListPage(int pageNum, String userid) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			ArrayList<LikeDto> likeList = new ArrayList<>();
			
			/*
			1page : 0 ~ 14
			2page : 15 ~ 29
			3page : 30 ~ 44
			*/
			int startNum = pageNum == 1 ? 0 : (pageNum - 1) * 7;
			
			try {
				// 1. 드라이버 준비
				Class.forName("com.mysql.cj.jdbc.Driver");

				// 2. 연결 객체 만들기
				conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");

				String sql = "SELECT l.prodno, l.userid, l.selectdate, l.likechecked, p.prodName, p.prodPrice, p.prodexplain "
							+ "FROM `like` l " 
							+ "LEFT OUTER JOIN product p " 
							+ "ON l.prodno = p.prodno "
							+ "WHERE userid = ? AND likechecked = true "
							+ "ORDER BY selectdate DESC "
							+ "LIMIT ?, 7";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setInt(2, startNum);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					LikeDto like = new LikeDto();
					like.setProdNo(rs.getInt(1));
					like.setUserId(rs.getString(2));
					like.setSelectDate(rs.getDate(3));
					like.setLikeChecked(rs.getBoolean(4));
					like.setProdName(rs.getString(5));
					like.setProdPrice(rs.getInt(6));
					like.setProdexplain(rs.getString(7));
					likeList.add(like);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				// 6. 연결 종료
				try { rs.close(); } catch (Exception ex) {}
				try { pstmt.close(); } catch (Exception ex) {}
				try { conn.close(); } catch (Exception ex) {}
			}
			return likeList;
		}

	public int getLikeCount(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int likeCount = 0;

		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
	
			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
	
			// 3. 명령 객체 만들기
			String sql = "SELECT COUNT(prodno) "
					+ "FROM hollys.like "
					+ "WHERE userid = ? AND likechecked = true ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
	
			// 5. 결과가 있으면 결과 처리
			if (rs.next()) {
				likeCount = rs.getInt(1);
				}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { conn.close(); } catch (Exception e) {}
		}
	
		return likeCount;
	}
}
