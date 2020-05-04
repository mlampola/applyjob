/*
 * (C) Markus Lampola 2016
 */
package com.lampola.applyjob.config;

/**
 *
 * @author Markus Lampola
 */
import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
/**
 *
 * @author Markus Lampola
 */
@Configuration
public class DevelopmentConfiguration {
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/h2-console/*");
        return registrationBean;
    }
}

