package com.stt.myshop.commons.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = "com.stt.myshop.commons.mapper")
@EnableTransactionManagement
public class MyBatisMapperConfig {
}
