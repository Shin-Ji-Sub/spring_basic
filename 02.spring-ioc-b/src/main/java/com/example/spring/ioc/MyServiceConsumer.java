package com.example.spring.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("serviceConsumer") // <bean id="serviceConsumer" class="com...MySerivceConsumer">와 같은 효과
public class MyServiceConsumer implements ServiceConsumer {

	// 1. 직접 인스턴스 생성 (new 생성)
	// private TimeService timeService = new MyTimeService(); 
	// private MessageService messageService = new MyMessageService();

	// 2. IoC 컨테이너를 사용해서 인스턴스 만들기
	public MyServiceConsumer() {}
	
	private TimeService timeService;
	// <property name="timeService"> 와 같은 효과 : setter injection
	@Autowired 
	public void setTimeService(TimeService timeService) {
		this.timeService = timeService;
	}
	
	private MessageService messageService;
	// <property name="messageService ref="messageService"> 와 같은 효과
	@Autowired @Qualifier("messageService")
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	public void doSomething() {
		String message = messageService.getMessage();
		System.out.println(message);
		message = timeService.getTimeString();
		System.out.println(message);
	}

}
