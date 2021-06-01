package com.ncit.finder.db;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.url("jdbc:postgresql://localhost:5432/postgres");
            dataSourceBuilder.username("postgres");
            dataSourceBuilder.password("root");
            return dataSourceBuilder.build();   
    }
}
