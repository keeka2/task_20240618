package com.example.sos.common.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class RoutingDataBaseConfig {
    @Primary
    @Bean
    public DataSource dataSource(final DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public DataSource routingDataSource(
            final @Qualifier(value = "readWriteDataSource") DataSource readWriteDataSource,
            final @Qualifier(value = "readOnlyDataSource") DataSource readOnlyDataSource) {
        return new RoutingDataSource(readWriteDataSource, readOnlyDataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read-write.hikari")
    public DataSource readWriteDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read-only.hikari")
    public DataSource readOnlyDataSource() {
        final HikariDataSource hikariDataSource =
                DataSourceBuilder.create().type(HikariDataSource.class).build();
        hikariDataSource.setReadOnly(true);
        return hikariDataSource;
    }
}
