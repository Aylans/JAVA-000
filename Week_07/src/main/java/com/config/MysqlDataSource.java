package com.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * created by Aylan
 * on 2020/12/2 8:25 下午
 */
@Configuration
public class MysqlDataSource {

    @Bean("masterDatasource")
    @ConfigurationProperties("spring.datasource.master")
    public HikariDataSource masterDatasource() {
        return new HikariDataSource();
    }

    @Bean("masterJdbcTemplate")
    public JdbcTemplate masterJdbcTemplate() {
        return new JdbcTemplate(masterDatasource());
    }


    @Bean("slave1Datasource")
    @ConfigurationProperties("spring.datasource.slave1")
    public HikariDataSource slave1Datasource() {
        return new HikariDataSource();
    }

    @Bean("slave1JdbcTemplate")
    public JdbcTemplate slave1JdbcTemplate() {
        return new JdbcTemplate(slave1Datasource());
    }


    @Bean("slave2Datasource")
    @ConfigurationProperties("spring.datasource.slave2")
    public HikariDataSource slave2Datasource() {
        return new HikariDataSource();
    }

    @Bean("slave2JdbcTemplate")
    public JdbcTemplate slave2JdbcTemplate() {
        return new JdbcTemplate(slave2Datasource());
    }

    @Bean
    public DynamicDataSourceConfig getDynamicDataSourceConfig(){
        Map<Object, Object> targetDataSources = new HashMap<>(5);
        targetDataSources.put(DataSourceConstant.MASTER, masterDatasource());
        targetDataSources.put(DataSourceConstant.SLAVE1, slave1Datasource());
        targetDataSources.put(DataSourceConstant.SLAVE2, slave2Datasource());
        return new DynamicDataSourceConfig(slave1Datasource(), targetDataSources);
    }
}
