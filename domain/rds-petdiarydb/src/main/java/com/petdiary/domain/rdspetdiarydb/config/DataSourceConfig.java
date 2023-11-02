package com.petdiary.domain.rdspetdiarydb.config;

import com.petdiary.domain.rdscore.ReplicationRoutingDataSource;
import com.petdiary.domain.rdscore.interfaces.IDomainMasterSlaveDataSourceConfig;
import com.petdiary.domain.rdscore.repository.ExtendedRepositoryImpl;
import com.petdiary.domain.rdspetdiarydb.PetDiaryConstants;
import com.petdiary.domain.rdspetdiarydb.properties.PetDiaryMasterDataSourceProperties;
import com.petdiary.domain.rdspetdiarydb.properties.PetDiarySlaveDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration("petDiaryDataSourceConfig")
@EnableTransactionManagement
@EnableJpaRepositories(
        repositoryBaseClass = ExtendedRepositoryImpl.class,
        entityManagerFactoryRef = PetDiaryConstants.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = PetDiaryConstants.TRANSACTION_MANAGER,
        basePackages = {"com.petdiary.domain.rdspetdiarydb.repository"}
)
@RequiredArgsConstructor
public class DataSourceConfig implements IDomainMasterSlaveDataSourceConfig {
    private final PetDiaryMasterDataSourceProperties masterDataSourceProperties;
    private final PetDiarySlaveDataSourceProperties slaveDataSourceProperties;

    @Override
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

    @Override
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

    @Override
    @Bean("petDiaryRoutingDataSource")
    public DataSource routingDataSource(
            @Qualifier("petDiaryMasterDataSource") DataSource masterDataSource,
            @Qualifier("petDiarySlaveDataSource") DataSource slaveDataSource
    ) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        routingDataSource.setMasterSlave(masterDataSource, slaveDataSource);
        return routingDataSource;
    }

    @Override
    @Bean("petDiaryDataSource")
    public DataSource dataSource(@Qualifier("petDiaryRoutingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Override
    @Bean(PetDiaryConstants.ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("petDiaryDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.petdiary.domain.rdspetdiarydb.domain")
                .persistenceUnit(PetDiaryConstants.ENTITY_MANAGER)
                .build();
    }

    @Override
    @Bean(PetDiaryConstants.TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager(
            @Qualifier(PetDiaryConstants.ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
