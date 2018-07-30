package com.github.peacetrue.raws;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.IsolationLevelDataSourceRouter;
import org.springframework.transaction.annotation.Isolation;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiayx
 */
@Configuration
public class RawsConfiguration {

    @Bean
    @ConfigurationProperties("app.datasource.write")
    public DataSourceProperties writeDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean
    @ConfigurationProperties("app.datasource.write")
    public DataSource writeDataSource() {
        return writeDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("app.datasource.read")
    public DataSourceProperties readDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean
    @ConfigurationProperties("app.datasource.read")
    public DataSource readDataSource() {
        return readDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public DataSource isolationLevelDataSourceRouter() {
        IsolationLevelDataSourceRouter dataSourceRouter = new IsolationLevelDataSourceRouter();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(Isolation.SERIALIZABLE.value(), writeDataSource());
        dataSourceMap.put(Isolation.READ_UNCOMMITTED.value(), readDataSource());
        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource());
        return dataSourceRouter;
    }

    @Bean
    public DataSource readWriteDataSourceRouter() {
        ReadWriteRoutingDataSource dataSourceRouter = new ReadWriteRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(ReadWriteRoutingDataSource.TYPE_WRITE, writeDataSource());
        dataSourceMap.put(ReadWriteRoutingDataSource.TYPE_READ, readDataSource());
        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource());
        return dataSourceRouter;
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(readWriteDataSourceRouter());
    }


}
