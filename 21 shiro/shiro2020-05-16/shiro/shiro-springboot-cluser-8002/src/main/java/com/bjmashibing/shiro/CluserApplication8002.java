package com.bjmashibing.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author sunzhiqiang23
 */
@MapperScan(basePackages = {"com.bjmashibing.shiro.moduler.*.mapper"})
@SpringBootApplication
public class CluserApplication8002 extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(CluserApplication8002.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CluserApplication8002.class);
    }
}