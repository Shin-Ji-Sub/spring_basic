package com.spring.coffeeorderproject.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.spring.coffeeorderproject.dao.AccountDao;
import com.spring.coffeeorderproject.dao.MySqlAccountDao;
import com.spring.coffeeorderproject.dao.MySqlAccountDaoWithMyBatis;
import com.spring.coffeeorderproject.service.AccountService;
import com.spring.coffeeorderproject.service.AccountServiceImpl;

@Configuration
public class RootConfiguration {
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		return factoryBean.getObject();
//		try {
//			return factoryBean.getObject();
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to create SqlSessionFactory", e);
//		}
	}
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://3.37.123.170:3306/hollys");
		dataSource.setUsername("hollys");
		dataSource.setPassword("mysql");
		
		dataSource.setInitialSize(10);
		dataSource.setMaxTotal(20);
		dataSource.setMaxIdle(20);
		
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	
	
//	@Bean
//	public AccountDao accountDao() {
//		MySqlAccountDao userDao = new MySqlAccountDao();
//		userDao.setJdbcTemplate(jdbcTemplate());
//		return userDao;
//	}
	
//	@Bean
//	public AccountService accountService() {
//		
//		AccountServiceImpl accountService = new AccountServiceImpl();
//		accountService.setAccountDao(accountDao());
//		return accountService;
//	}
	
	@Bean
	public AccountDao accountDao() throws Exception {
		MySqlAccountDaoWithMyBatis userDao = new MySqlAccountDaoWithMyBatis();
		userDao.setSqlSessionTemplate(sqlSessionTemplate());
		return userDao;
	}
	
	@Bean
	public AccountService accountService() throws Exception {
		
		AccountServiceImpl accountService = new AccountServiceImpl();
		accountService.setAccountDao(accountDao());
		return accountService;
	}
	

}
