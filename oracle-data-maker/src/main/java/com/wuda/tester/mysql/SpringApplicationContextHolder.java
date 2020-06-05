package com.wuda.tester.mysql;

import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * spring application context.
 *
 * @author wuda
 */
public class SpringApplicationContextHolder {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringApplicationContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return SpringApplicationContextHolder.applicationContext;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+0")).toEpochMilli());
    }

}
