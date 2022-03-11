package com.bjmashibing.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author sunzhiqiang23
 */
@SpringBootApplication
public class SsoClientApplication9003 extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SsoClientApplication9003.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SsoClientApplication9003.class);
    }
}