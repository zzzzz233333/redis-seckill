package com.zy.test.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author {庄勇}
 * @since 2022/10/10 9:50
 */
@Configuration
@MapperScan(basePackages = "com.zy.test.mapper")
public class MyBatisConfiguration {
}
