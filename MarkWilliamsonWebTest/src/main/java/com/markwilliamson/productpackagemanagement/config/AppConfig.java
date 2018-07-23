package com.markwilliamson.productpackagemanagement.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.markwilliamson.productpackagemanagement")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class AppConfig{
	//
}
