package com.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

//https://www.cnblogs.com/elvinle/p/8177825.html
@EnableEurekaClient
@SpringBootApplication
//@ComponentScan({ "com.framework.*" })
public class AppSystemMq {

    public static void main(String[] args) {
        SpringApplication.run(AppSystemMq.class);
    }
}
