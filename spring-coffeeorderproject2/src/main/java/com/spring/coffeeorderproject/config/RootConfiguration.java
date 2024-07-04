package com.spring.coffeeorderproject.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.coffeeorderproject.mapper.UserMapper;
import com.spring.coffeeorderproject.service.AccountService;
import com.spring.coffeeorderproject.service.AccountServiceImpl;

@Configuration
@MapperScan(basePackages = "com.coffeeorderproject.mapper")
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
	
	@Bean
	public AccountService accountService(UserMapper userMapper) throws Exception {
		
		AccountServiceImpl accountService = new AccountServiceImpl();
		accountService.setUserMapper(userMapper);
		return accountService;
	}
	

}
