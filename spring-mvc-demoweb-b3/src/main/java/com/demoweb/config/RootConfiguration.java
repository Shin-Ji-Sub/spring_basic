package com.demoweb.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.demoweb.dao.MemberDao;
import com.demoweb.dao.OracleMemberDao;
import com.demoweb.dao.OracleMemberDaoWithConnectionPool;
import com.demoweb.dao.OracleMemberDaoWithJdbcTemplate;
import com.demoweb.dao.OracleMemberDaoWithMyBatis;
import com.demoweb.dao.OracleMemberDaoWithNamedParameterJdbcTemplate;
import com.demoweb.mapper.BoardMapper;
import com.demoweb.mapper.MemberMapper;
import com.demoweb.service.AccountService;
import com.demoweb.service.AccountServiceImpl;
import com.demoweb.service.BoardService;
import com.demoweb.service.BoardServiceImpl;

@Configuration
@MapperScan(basePackages = { "com.demoweb.mapper" })  // == <mybatis:scan base-package="com.demoweb.mapper"/>
public class RootConfiguration {
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521/xe");
		dataSource.setUsername("green_cloud");
		dataSource.setPassword("oracle");
		
		dataSource.setInitialSize(10);
		dataSource.setMaxTotal(20);
		dataSource.setMaxIdle(10);
		
		return dataSource;
	}
	
	@Bean SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		return factoryBean.getObject();
	}
	
	@Bean AccountService accountService(MemberMapper memberMapper) throws Exception {
		AccountServiceImpl accountService = new AccountServiceImpl();
		accountService.setMemberMapper(memberMapper);
		return accountService;
	}
	
	@Bean BoardService boardService(BoardMapper boardMapper) throws Exception {
		BoardServiceImpl boardService = new BoardServiceImpl();
		boardService.setBoardMapper(boardMapper);
		return boardService;
	}
	
}







