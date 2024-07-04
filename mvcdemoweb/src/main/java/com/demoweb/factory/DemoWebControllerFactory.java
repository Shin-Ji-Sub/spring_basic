package com.demoweb.factory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.demoweb.controller.Controller;
import com.demoweb.dao.AppSettingsDao;
import com.demoweb.dao.BoardDao;
import com.demoweb.dao.MemberDao;
import com.demoweb.dao.MySqlAppSettingsDao;
import com.demoweb.dao.OracleAppSettingsDao;

public class DemoWebControllerFactory {
	
	static Properties mappings;
	
	public DemoWebControllerFactory() {
		
		mappings = new Properties();
		
		try {
			// 클래스패스 경로에서 파일 읽기
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("com/demoweb/config/controller-config.properties");
			mappings.load(is);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	// Generic 호환성 활용한 객체 생성기
	// static method 일 때 Generic
	public Controller getInstance(String action) {
		
		Controller controller = null;
		
		try {
			// reflection을 사용해서 인스턴스 만들기
			String className = mappings.getProperty(action);                      // 클래스 이름 읽기 (패키지명 포함)
			Class claz = Class.forName(className);                                // 클래스 이름으로 클래스 정보 객체 생성
			controller = (Controller)claz.getDeclaredConstructor().newInstance(); // 클래스 정보 객체를 사용해서 인스턴스 만들기 (new X)
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return controller;
		
	}

}
