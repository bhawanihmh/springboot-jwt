package com.jwtexample.config;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author bhawani.singh
 *
 * JWT example application
 */

@SpringBootApplication
@ComponentScan("com.jwtexample")
public class JwtExampleApp  extends SpringBootServletInitializer {
	
	private static final Logger LOGGER = Logger.getLogger(JwtExampleApp.class.getName());
	
	/*@Bean
	public ViewResolver configureViewResolver() {
		LOGGER.info("configureViewResolver is called ");
		InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
		viewResolve.setPrefix("/WEB-INF/jsp/");
		viewResolve.setSuffix(".jsp");
		viewResolve.setOrder(2);
		viewResolve.setViewClass(JstlView.class);
		return viewResolve;
	}*/
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JwtExampleApp.class);
	}

	public static void main(String[] args) throws Exception {
		
		LOGGER.info("Start Spring Application");
		
		SpringApplication.run(JwtExampleApp.class, args);
	}
}
