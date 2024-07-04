package com.spring.coffeeorderproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.spring.coffeeorderproject.dto.ProductDto;
import com.spring.coffeeorderproject.dto.CartDto;
import com.spring.coffeeorderproject.dto.UserDto;

public class CartDao {

	public void inputProductIntoCart(int productCount, int prodno, String userId) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "INSERT INTO hollys.like (prodno, userid, cartquantity, likechecked) VALUES (?, ?, ?, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, prodno);
			pstmt.setString(2, userId);
			pstmt.setInt(3, productCount);
			pstmt.setBoolean(4, false);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			pstmt.executeUpdate();
			
			// 5. 결과가 있으면 결과 처리
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
	}

	public Boolean selectIsUserInCart(String userId, int prodno) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Boolean isUser = null;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "SELECT prodno, userid, cartquantity, selectdate, likechecked FROM hollys.like "
					+ "WHERE userid = ? AND likechecked = 0 AND prodno = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, prodno);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			if (rs.next()) {
				isUser = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
		return isUser;
	}

	public void updateProductCount(int productCount, int prodno, String userId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "UPDATE hollys.like SET cartquantity = cartquantity + ? WHERE userid = ? AND prodno = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, productCount);
			pstmt.setString(2, userId);
			pstmt.setInt(3, prodno);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			pstmt.executeUpdate();
			
			// 5. 결과가 있으면 결과 처리
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
	}

	public ArrayList<CartDto> selectAllCartProduct(String userId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CartDto> productArr = new ArrayList<>();
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "SELECT hl.prodno, userid, cartquantity, p.productcategoryid, p.prodname, p.prodprice, p.prodactive "
					+ "FROM hollys.like hl "
					+ "LEFT JOIN product p "
					+ "ON hl.prodno = p.prodno "
					+ "WHERE userid = ? AND hl.likechecked = 0";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				CartDto cart = new CartDto();
				cart.setProdNo(rs.getInt(1));
				cart.setUserId(rs.getString(2));
				cart.setCartquantity(rs.getInt(3));
				cart.setProductCategoryId(rs.getInt(4));
				cart.setProdName(rs.getString(5));
				cart.setProdPrice(rs.getInt(6));
				cart.setProdActive(rs.getBoolean(7));
				
				productArr.add(cart);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
		return productArr;
	}

	public void deleteProduct(int prodNo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "DELETE FROM hollys.like WHERE prodno = ? AND likechecked = false";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, prodNo);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			pstmt.executeUpdate();
			
			// 5. 결과가 있으면 결과 처리
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
	}
	
	public ArrayList<CartDto> selectUserCart(String userId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CartDto> userCartArr = new ArrayList<>();
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "SELECT hl.prodno, u.userid, cartquantity, p.productcategoryid, p.prodname, p.prodprice, p.prodactive "
					+ "FROM user u "
					+ "LEFT JOIN hollys.like hl "
					+ "ON u.userid = hl.userid "
					+ "LEFT JOIN product p "
					+ "ON hl.prodno = p.prodno "
					+ "WHERE u.userid = ? AND hl.likechecked = 0";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				CartDto cart = new CartDto();
				cart.setProdNo(rs.getInt(1));
				cart.setUserId(rs.getString(2));
				cart.setCartquantity(rs.getInt(3));
				cart.setProductCategoryId(rs.getInt(4));
				cart.setProdName(rs.getString(5));
				cart.setProdPrice(rs.getInt(6));
				cart.setProdActive(rs.getBoolean(7));
				
				userCartArr.add(cart);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
		return userCartArr;
	}

}
