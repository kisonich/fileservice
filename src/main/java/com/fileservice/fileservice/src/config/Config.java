package com.fileservice.fileservice.src.config;

import com.fileservice.fileservice.FileserviceApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.fileservice.fileservice.src" })
@PropertySource("classpath:application.properties")
@Import({FileserviceApplication.class})
public class Config {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
}
