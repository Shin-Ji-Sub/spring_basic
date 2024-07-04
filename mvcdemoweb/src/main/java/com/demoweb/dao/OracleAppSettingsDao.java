package com.demoweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.demoweb.dto.AppSettingDto;
import com.demoweb.dto.MemberDto;

public class OracleAppSettingsDao implements AppSettingsDao {
	
	private Connection getConnection() throws Exception {
		// 1. 드라이버 준비
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		// 2. 연결 객체 만들기
		return DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/xe", "green_cloud", "oracle");
	}
	
	@Override
	public AppSettingDto selectAppSetting(String settingName) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AppSettingDto appSetting = null;
		
		try {
			// 1. 드라이버 준비
			// 2. 연결 객체 만들기
			connection = getConnection();
			
			// 3. 명령 객체 만들기
			String sql = "SELECT setting_name, setting_value "
					+ "FROM app_settings "
					+ "WHERE setting_name = ? ";  
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, settingName);  
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - select 인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			if (rs.next()) {
				appSetting = new AppSettingDto();
				appSetting.setSettingName(rs.getString(1));
				appSetting.setSettingValue(rs.getString(2));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 연결 종료
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
		return appSetting;
	}
	
	@Override
	public void updateSettingName(AppSettingDto appSetting) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. 드라이버 준비
			// 2. 연결 객체 만들기
			connection = getConnection();
			
			// 3. 명령 객체 만들기
			String sql = "UPDATE app_settings "
					+ "SET setting_value = ? "
					+ "WHERE setting_name = ? ";  
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, appSetting.getSettingValue());
			pstmt.setString(2, appSetting.getSettingName());
			
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
}
