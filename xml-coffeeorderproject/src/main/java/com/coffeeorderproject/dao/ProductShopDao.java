package com.coffeeorderproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import admin.coffeeorderproject.dto.ProductDto;
import user.coffeeorderproject.dto.BoardDto;

public class ProductShopDao {
	
	public ArrayList<ProductDto> selectAllProducts(int pageNum, int selectValue) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProductDto> productArr = new ArrayList<>();
		
		/*
		1page : 0 ~ 8
		2page : 9 ~ 17
		3page : 18 ~ 27
		*/
		int startNum = pageNum == 1 ? 0 : (pageNum - 1) * 9;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = null;
			switch (selectValue) {
			case 0 : sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive FROM product "
					+ "WHERE prodactive = false "
					+ "LIMIT ?, 9"; break;
			case 1 : sql = "SELECT p.prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive "
					+ "FROM product p "
					+ "LEFT JOIN hollys.like hl "
					+ "ON p.prodno = hl.prodno "
					+ "WHERE prodactive = false "
					+ "GROUP BY p.prodno "
					+ "ORDER BY COUNT(hl.prodno) DESC "
					+ "LIMIT ?, 9"; break;
			case 2 : sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive FROM product "
					+ "WHERE prodactive = false "
					+ "ORDER BY prodprice ASC "
					+ "LIMIT ?, 9"; break;
			}
//			String sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive FROM product "
//					+ "LIMIT ?, 9";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, startNum);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				ProductDto product = new ProductDto();
				product.setProdNo(rs.getInt(1));
				product.setProductCategoryId(rs.getInt(2));
				product.setProdName(rs.getString(3));
				product.setProdPrice(rs.getInt(4));
				product.setProdexplain(rs.getString(5));
				product.setProdActive(rs.getBoolean(6));
				
				productArr.add(product);
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

	public ArrayList<ProductDto> selectCategoryProduct(int cateNum, int selectValue, int pageNum) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProductDto> productArr = new ArrayList<>();
		int startNum = pageNum == 1 ? 0 : (pageNum - 1) * 9;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = null;
			switch (selectValue) {
			case 0: sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive FROM product "
					+ "WHERE productcategoryid = ? AND prodactive = false "
					+ "LIMIT ?, 9"; break;
			case 1: sql = "SELECT p.prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive "
					+ "FROM product p "
					+ "LEFT JOIN hollys.like hl "
					+ "ON p.prodno = hl.prodno "
					+ "WHERE productcategoryid = ? AND prodactive = false "
					+ "GROUP BY p.prodno "
					+ "ORDER BY COUNT(hl.prodno) DESC "
					+ "LIMIT ?, 9"; break;
			case 2: sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive FROM product "
					+ "WHERE productcategoryid = ? AND prodactive = false "
					+ "ORDER BY prodprice ASC "
					+ "LIMIT ?, 9"; break;
			}
//			String sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive FROM product "
//					+ "WHERE productcategoryid = ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, cateNum);
			pstmt.setInt(2, startNum);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				ProductDto product = new ProductDto();
				product.setProdNo(rs.getInt(1));
				product.setProductCategoryId(rs.getInt(2));
				product.setProdName(rs.getString(3));
				product.setProdPrice(rs.getInt(4));
				product.setProdexplain(rs.getString(5));
				product.setProdActive(rs.getBoolean(6));
				
				productArr.add(product);
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

	public ArrayList<ProductDto> selectSearchProduct(String searchKeyword, int pageNum, int selectValue) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProductDto> productArr = new ArrayList<>();
		int startNum = pageNum == 1 ? 0 : (pageNum - 1) * 9;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = null;
			switch (selectValue) {
			case 0: sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive FROM product "
					+ "WHERE prodname LIKE ? AND prodactive = false "
					+ "LIMIT ?, 9"; break;
			case 1: sql = "SELECT p.prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive "
					+ "FROM product p "
					+ "LEFT JOIN hollys.like hl "
					+ "ON p.prodno = hl.prodno "
					+ "WHERE prodname LIKE ? AND prodactive = false "
					+ "GROUP BY p.prodno "
					+ "ORDER BY COUNT(hl.prodno) DESC "
					+ "LIMIT ?, 9"; break;
			case 2: sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive FROM product "
					+ "WHERE prodname LIKE ? AND prodactive = false "
					+ "ORDER BY prodprice ASC "
					+ "LIMIT ?, 9"; break;
			}
			
//			String sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive FROM product "
//					+ "WHERE prodname LIKE ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "%" + searchKeyword + "%");
			pstmt.setInt(2, startNum);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				ProductDto product = new ProductDto();
				product.setProdNo(rs.getInt(1));
				product.setProductCategoryId(rs.getInt(2));
				product.setProdName(rs.getString(3));
				product.setProdPrice(rs.getInt(4));
				product.setProdexplain(rs.getString(5));
				product.setProdActive(rs.getBoolean(6));
				
				productArr.add(product);
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

	public ArrayList<ProductDto> selectSortProduct(int selectValue) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProductDto> productArr = new ArrayList<>();
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = null;
			switch (selectValue) {
			case 1 : sql = "SELECT p.prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive "
					+ "FROM product p "
					+ "LEFT JOIN hollys.like hl "
					+ "ON p.prodno = hl.prodno "
					+ "WHERE prodactive = false "
					+ "GROUP BY p.prodno "
					+ "ORDER BY COUNT(hl.prodno) DESC"; break;
			case 2 : sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive FROM product "
					+ "WHERE prodactive = false "
					+ "ORDER BY prodprice ASC"; break;
			}
//			String sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive FROM product "
//					+ "ORDER BY prodprice ASC";
			pstmt = connection.prepareStatement(sql);
//			pstmt.setString(1, "%" + searchKeyword + "%");
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				ProductDto product = new ProductDto();
				product.setProdNo(rs.getInt(1));
				product.setProductCategoryId(rs.getInt(2));
				product.setProdName(rs.getString(3));
				product.setProdPrice(rs.getInt(4));
				product.setProdexplain(rs.getString(5));
				product.setProdActive(rs.getBoolean(6));
				
				productArr.add(product);
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

	public int getProductCount() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int productCount = 0;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "SELECT COUNT(prodno) FROM product "
					+ "WHERE prodactive = false ";
			pstmt = connection.prepareStatement(sql);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			if (rs.next()) {
				productCount = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
		return productCount;
	}

	public ProductDto selectDetailProduct(int prodno) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductDto product = new ProductDto();
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "SELECT prodno, p.productcategoryid, prodname, prodprice, prodexplain, prodactive, pc.productcategoryname "
					+ "FROM product p "
					+ "LEFT JOIN productcategory pc "
					+ "ON p.productcategoryid = pc.productcategoryid "
					+ "WHERE prodno = ? AND prodactive = false ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, prodno);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			if (rs.next()) {
				product.setProdNo(rs.getInt(1));
				product.setProductCategoryId(rs.getInt(2));
				product.setProdName(rs.getString(3));
				product.setProdPrice(rs.getInt(4));
				product.setProdexplain(rs.getString(5));
				product.setProdActive(rs.getBoolean(6));
				product.setProductCategoryName(rs.getString(7));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
		return product;
	}

	public ArrayList<ProductDto> selectDetailCateProduct(int prodno) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProductDto> products = new ArrayList<>();
		
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "SELECT prodno, p.productcategoryid, prodname, prodprice, prodexplain, prodactive, pc.productcategoryname "
					+ "FROM product p "
					+ "LEFT JOIN productcategory pc "
					+ "ON p.productcategoryid = pc.productcategoryid "
					+ "WHERE p.productcategoryid = ( "
					+ "SELECT productcategoryid FROM product WHERE prodno = ?) AND prodactive = false ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, prodno);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				ProductDto product = new ProductDto();
				product.setProdNo(rs.getInt(1));
				product.setProductCategoryId(rs.getInt(2));
				product.setProdName(rs.getString(3));
				product.setProdPrice(rs.getInt(4));
				product.setProdexplain(rs.getString(5));
				product.setProdActive(rs.getBoolean(6));
				product.setProductCategoryName(rs.getString(7));
				
				products.add(product);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
		return products;
	}

	public ArrayList<ProductDto> selectAllproduct() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProductDto> productArr = new ArrayList<>();
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			/*
			 * "SELECT prodno, p.productcategoryid, prodname, prodprice, prodexplain, prodactive, pc.productcategoryname "
			 * + "FROM product p " + "LEFT JOIN productcategory pc " +
			 * "ON p.productcategoryid = pc.productcategoryid " +
			 * "WHERE prodno = ? AND prodactive = false ";
			 */
			String sql = "SELECT prodno, p.productcategoryid, prodname, prodprice, prodexplain, prodactive, pc.productcategoryname "
					+ "FROM product p " + "LEFT JOIN productcategory pc " 
					+ "ON p.productcategoryid = pc.productcategoryid " 
					+ "WHERE prodactive = false ";
							
			pstmt = connection.prepareStatement(sql);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				ProductDto product = new ProductDto();
				product.setProdNo(rs.getInt(1));
				product.setProductCategoryId(rs.getInt(2));
				product.setProdName(rs.getString(3));
				product.setProdPrice(rs.getInt(4));
				product.setProdexplain(rs.getString(5));
				product.setProdActive(rs.getBoolean(6));
				product.setProductCategoryName(rs.getString(7));
				
				productArr.add(product);
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

	public ArrayList<BoardDto> selectProductReview(int prodno) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDto> reviewArr = new ArrayList<>();
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "SELECT b.boardno, b.userid, boardmodifydate, boardcontent, hu.usernickname "
					+ "FROM board b "
					+ "LEFT JOIN productreview pr "
					+ "ON b.boardno = pr.boardno "
					+ "LEFT JOIN product p "
					+ "ON pr.prodno = p.prodno "
					+ "LEFT JOIN hollys.user hu "
					+ "ON b.userid = hu.userid "
					+ "WHERE p.prodno = ? AND boarddelete = false AND prodactive = false "
					+ "ORDER BY boardmodifydate DESC "
					+ "LIMIT 5";
							
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, prodno);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				BoardDto board = new BoardDto();
				board.setBoardNo(rs.getInt(1));
				board.setUserId(rs.getString(2));
				board.setBoardModifyDate(rs.getTimestamp(3));
				board.setBoardContent(rs.getString(4));
				board.setUsernickname(rs.getString(5));
				
				reviewArr.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
		return reviewArr;
	}

	public int selectProductsSize(int cateNum) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int productsSize = 0;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "SELECT COUNT(*) FROM product "
						+ "WHERE productcategoryid = ? AND prodactive = false ";
							
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, cateNum);
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			if (rs.next()) {
				productsSize = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
		return productsSize;
	}

}
