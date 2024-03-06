package com.ajerryz.demo.ioc;

import com.ajerryz.demo.beans.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jerry zhang
 * @since 2024/3/6 21:21
 */
public class FromXmlDemo {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		Person person = context.getBean(Person.class);
		System.out.println(person);
	}
}
