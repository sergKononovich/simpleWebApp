package com.mastery.java.task.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.jdbc.Sql;

@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("classpath:dao.properties")
public class AppConfiguration {

}
