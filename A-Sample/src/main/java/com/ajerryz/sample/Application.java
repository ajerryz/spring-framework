package com.ajerryz.sample;

import com.ajerryz.sample.beans.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author jerry zhang
 * @since 2024/4/24 00:14
 */
@Configuration
@ComponentScan("com.ajerryz.sample")
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
		Person person = context.getBean(Person.class);
		System.out.println(person);
	}
}
