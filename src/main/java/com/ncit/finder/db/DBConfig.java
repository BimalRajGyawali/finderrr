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
            String host = "ec2-107-20-153-39.compute-1.amazonaws.com";
            String database = "d7nsl19ehlcnh5";
            String username = "hrpvddnprmsjod";
            String port = "5432";
            String password = "201123a3c686a22eb14f50e256d81c710457a236b249ebb57665a9ba5b18d3bc";

            // String host = "localhost";
            // String database = "finder";
            // String username = "postgres";
            // String port = "5432";
            // String password = "root";


            String url = "jdbc:postgresql://"+host+":"+port+"/"+database;

            dataSourceBuilder.url(url);
            dataSourceBuilder.username(username);
            dataSourceBuilder.password(password);
            return dataSourceBuilder.build();   
    }
}
