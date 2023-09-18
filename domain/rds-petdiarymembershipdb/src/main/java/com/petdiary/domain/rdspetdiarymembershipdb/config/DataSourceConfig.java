package com.petdiary.domain.rdspetdiarymembershipdb.config;

import com.petdiary.domain.rdscore.NoOpDataSource;
import com.petdiary.domain.rdscore.ReplicationRoutingDataSource;
import com.petdiary.domain.rdspetdiarymembershipdb.properties.PetDiaryMembershipMasterDataSourceProperties;
import com.petdiary.domain.rdspetdiarymembershipdb.properties.PetDiaryMembershipSlaveDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration("petDiaryMembershipDataSourceConfig")
@RequiredArgsConstructor
public class DataSourceConfig {
    private final PetDiaryMembershipMasterDataSourceProperties masterDataSourceProperties;
    private final PetDiaryMembershipSlaveDataSourceProperties slaveDataSourceProperties;

    @Bean("petDiaryMembershipMasterDataSource")
    public DataSource masterDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(masterDataSourceProperties.getJdbcUrl());
        dataSource.setUsername(masterDataSourceProperties.getUsername());
        dataSource.setPassword(masterDataSourceProperties.getPassword());
        dataSource.setDriverClassName(masterDataSourceProperties.getDriverClassName());
        dataSource.setMaximumPoolSize(masterDataSourceProperties.getMaximumPoolSize());
        return dataSource;
    }

    @Bean("petDiaryMembershipSlaveDataSource")
    public DataSource slaveDataSource() {
        if (!slaveDataSourceProperties.isEnabled()) {
            return DataSourceBuilder.create().type(NoOpDataSource.class).build();
        }

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(slaveDataSourceProperties.getJdbcUrl());
        dataSource.setUsername(slaveDataSourceProperties.getUsername());
        dataSource.setPassword(slaveDataSourceProperties.getPassword());
        dataSource.setDriverClassName(slaveDataSourceProperties.getDriverClassName());
        dataSource.setMaximumPoolSize(slaveDataSourceProperties.getMaximumPoolSize());
        return dataSource;
    }

    @Bean("petDiaryMembershipRoutingDataSource")
    public DataSource routingDataSource(
            @Qualifier("petDiaryMembershipMasterDataSource") DataSource masterDataSource,
            @Qualifier("petDiaryMembershipSlaveDataSource") DataSource slaveDataSource
    ) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        routingDataSource.setMasterSlave(masterDataSource, slaveDataSource);
        return routingDataSource;
    }
}
