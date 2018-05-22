package com.code.codesnippets.datasources;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "vis-pro")
    @ConfigurationProperties(prefix = "spring.datasource.vis-pro") // application.properteis中对应属性的前缀
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "zqy-pro")
    @ConfigurationProperties(prefix = "spring.datasource.zqy-pro") // application.properteis中对应属性的前缀
    public DataSource dataSource3() {
        return DataSourceBuilder.create().build();
    }
    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Bean(name = "dynamicDS1")
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource1());

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap(5);
        dsMap.put("vis-pro", dataSource1());
        dsMap.put("zqy-pro", dataSource3());

        dynamicDataSource.setTargetDataSources(dsMap);

        return dynamicDataSource;
    }

}