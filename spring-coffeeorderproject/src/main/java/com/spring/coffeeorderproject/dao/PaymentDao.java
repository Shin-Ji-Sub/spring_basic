package user.coffeeorderproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import admin.coffeeorderproject.dto.OrdersDto;
import admin.coffeeorderproject.dto.ProductDto;
import admin.coffeeorderproject.dto.RevenueDto;

public class PaymentDao {

	
	// 예시
//	SELECT ci.cart_id, ci.product_id, p.prodname, p.prodprice, ci.quantity, 
//    (p.prodprice * ci.quantity) as total_price 
//    FROM cart c 
//    INNER JOIN cart_items ci ON c.cart_id = ci.cart_id 
//    INNER JOIN product p ON ci.product_id = p.prodno 
//    WHERE c.customer_id = @customer_id 
	
	public ArrayList<ProductDto> selectOrderProduct(String text) { // 결제페이지 상품 출력 임시 장바구니 만들어지면 수정
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProductDto> ordersList = new ArrayList<>();

		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys?serverTimezone=UTC", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "SELECT prodno, productcategoryid, prodname, prodprice, prodexplain, prodactive "
					+ "FROM product "
					+ "WHERE prodname IN(?, ?)";
		
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, text);
			pstmt.setString(2, "카라멜 마키아또");
			
			
			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			rs = pstmt.executeQuery(); // select - executeQuery로 실행
			
			// 5. 결과가 있으면 결과 처리
			while (rs.next()) {
				ProductDto product = new ProductDto();
				product.setProdNo(rs.getInt(1));
				product.setProductCategoryId(rs.getInt(2));
				product.setProdName(rs.getString(3));
				product.setProdPrice(rs.getInt(4));
				product.setProdexplain(rs.getString(5));
				product.setProdActive(rs.getBoolean(6));
				
				ordersList.add(product);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return ordersList;
	}
	
	
	public void updateCartQuantity(int prodNo, int cartQuantity, String userId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys?serverTimezone=UTC", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "UPDATE hollys.like SET cartquantity = ? WHERE userid = ? AND prodno = ? AND likechecked = 0";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, cartQuantity);
			pstmt.setString(2, userId);
			pstmt.setInt(3, prodNo);
			
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

	public void insertOrder(OrdersDto orders, String userId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys?serverTimezone=UTC", "hollys", "mysql");
			
			
			
			// orders insert
			String sql = "INSERT INTO orders(userid, orderoption, orderstatus, orderpayment, couponuse) VALUES(?, ?, ?, ?, ?)";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, orders.getUserId());
			pstmt.setString(2, orders.getOrderOption());
			pstmt.setString(3, "결제완료");
			pstmt.setString(4, orders.getOrderPayment());
			pstmt.setBoolean(5, orders.isCouponuse());
			
			pstmt.executeUpdate();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
	}
	
	public void insertOrderDetail(int prodNo, int cartQuantity) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int lastOrderid = 0;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys?serverTimezone=UTC", "hollys", "mysql");
			
			String sql ="";
			
	        sql = "SELECT orderid FROM orders ORDER BY orderid DESC LIMIT 1 ";
	        // 수정된 부분: PreparedStatement 객체 할당
	        pstmt = connection.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        if(rs.next()) {
	            lastOrderid = rs.getInt(1);
	        }
	        pstmt.close();
			
			// orderdetail insert
			sql = "INSERT INTO orderdetail VALUES(?, ?, ?)";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setInt(1, prodNo);
			pstmt.setInt(2, lastOrderid);
			pstmt.setInt(3, cartQuantity);
			
			pstmt.executeUpdate();
	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
	}
	
	public void deleteCoupon(String userId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			connection = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys?serverTimezone=UTC", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "DELETE FROM coupon WHERE userid = ? AND couponactive = 0 "
					+ "ORDER BY couponstartdate ASC LIMIT 1";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			
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
	
	public int selectUserOrdersCount(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			// 1. 드라이버 준비
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. 연결 객체 만들기
			conn = DriverManager.getConnection("jdbc:mysql://3.37.123.170:3306/hollys", "hollys", "mysql");
			
			// 3. 명령 객체 만들기
			String sql = "SELECT sum(cartquantity) FROM orders o "
					+ "INNER JOIN orderdetail od ON o.orderid = od.orderid "
					+ "WHERE o.userid = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			// 4. 명령 실행 ( 결과가 있으면 결과 저장 - select 인 경우 )
			rs = pstmt.executeQuery(); // select - executeQuery로 실행
			
			// 5. 결과가 있으면 결과 처리
			if (rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return count;
	}

}
