package com.petdiary.domain.rdspetdiarydb.config;

import com.petdiary.domain.rdscore.ReplicationRoutingDataSource;
import com.petdiary.domain.rdspetdiarydb.properties.PetDiaryMasterDataSourceProperties;
import com.petdiary.domain.rdspetdiarydb.properties.PetDiarySlaveDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration("petDiaryDataSourceConfig")
@RequiredArgsConstructor
public class DataSourceConfig {
    private final PetDiaryMasterDataSourceProperties masterDataSourceProperties;
    private final PetDiarySlaveDataSourceProperties slaveDataSourceProperties;

    @Bean("petDiaryMasterDataSource")
    public DataSource masterDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(masterDataSourceProperties.getJdbcUrl());
        dataSource.setUsername(masterDataSourceProperties.getUsername());
        dataSource.setPassword(masterDataSourceProperties.getPassword());
        dataSource.setDriverClassName(masterDataSourceProperties.getDriverClassName());
        dataSource.setMaximumPoolSize(masterDataSourceProperties.getMaximumPoolSize());
        return dataSource;
    }

    @Bean("petDiarySlaveDataSource")
    public DataSource slaveDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(slaveDataSourceProperties.getJdbcUrl());
        dataSource.setUsername(slaveDataSourceProperties.getUsername());
        dataSource.setPassword(slaveDataSourceProperties.getPassword());
        dataSource.setDriverClassName(slaveDataSourceProperties.getDriverClassName());
        dataSource.setMaximumPoolSize(slaveDataSourceProperties.getMaximumPoolSize());
        return dataSource;
    }

    @Bean("petDiaryRoutingDataSource")
    public DataSource routingDataSource(
            @Qualifier("petDiaryMasterDataSource") DataSource masterDataSource,
            @Qualifier("petDiarySlaveDataSource") DataSource slaveDataSource
    ) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        routingDataSource.setMasterSlave(masterDataSource, slaveDataSource);
        return routingDataSource;
    }
}
