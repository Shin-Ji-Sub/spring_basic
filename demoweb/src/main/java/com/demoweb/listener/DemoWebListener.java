package com.demoweb.listener;

import com.demoweb.dto.AppSettingDto;
import com.demoweb.service.AppSettingsService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

// xml 등록을 자동으로
@WebListener
public class DemoWebListener implements ServletContextListener, HttpSessionListener {
	
	private AppSettingsService appSettingService = new AppSettingsService();
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		AppSettingDto appSetting = appSettingService.findAppSettingBySettingName("total_counter");
		
		// ServletContext = JSP의 application 내장객체
		ServletContext application = sce.getServletContext();
		application.setAttribute("current", 0);
		application.setAttribute("total", Integer.parseInt(appSetting.getSettingValue()));
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		ServletContext application = se.getSession().getServletContext();
		int current = (int)application.getAttribute("current");
		int total = (int)application.getAttribute("total");
		application.setAttribute("current", current + 1);
		application.setAttribute("total", total + 1);
		
		// 1000 카운트마다 한 번씩 데이터베이스에 저장
		if (total % 1000 == 0) {
			AppSettingDto appSetting = new AppSettingDto("total_counter", String.valueOf(total));
			appSettingService.modifyAppSetting(appSetting);
		}
		
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext application = se.getSession().getServletContext();
		int current = (int)application.getAttribute("current");
		int total = (int)application.getAttribute("total");
		application.setAttribute("current", current - 1);
//		application.setAttribute("total", total - 1);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// ServletContext = JSP의 application 내장객체
		ServletContext application = sce.getServletContext();
		int total = (int)application.getAttribute("total");
		AppSettingDto appSetting = new AppSettingDto("total_counter", String.valueOf(total));
		appSettingService.modifyAppSetting(appSetting);
	}
}
