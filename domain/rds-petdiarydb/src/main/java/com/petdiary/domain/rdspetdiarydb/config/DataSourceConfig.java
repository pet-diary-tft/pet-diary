package com.petdiary.domain.rdspetdiarydb.config;

import com.petdiary.domain.rdscore.ReplicationRoutingDataSource;
import com.petdiary.domain.rdscore.repository.ExtendedRepositoryImpl;
import com.petdiary.domain.rdspetdiarydb.properties.PetDiaryMasterDataSourceProperties;
import com.petdiary.domain.rdspetdiarydb.properties.PetDiarySlaveDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        repositoryBaseClass = ExtendedRepositoryImpl.class,
        entityManagerFactoryRef = "petdiaryEntityManagerFactory",
        transactionManagerRef = "petdiaryTransactionManager",
        basePackages = {"com.petdiary.domain.rdspetdiarydb.repository"}
)
@RequiredArgsConstructor
public class DataSourceConfig {
    private final PetDiaryMasterDataSourceProperties masterDataSourceProperties;
    private final PetDiarySlaveDataSourceProperties slaveDataSourceProperties;

    @Bean("petdiaryMasterDataSource")
    public DataSource masterDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(masterDataSourceProperties.getJdbcUrl());
        dataSource.setUsername(masterDataSourceProperties.getUsername());
        dataSource.setPassword(masterDataSourceProperties.getPassword());
        dataSource.setDriverClassName(masterDataSourceProperties.getDriverClassName());
        dataSource.setMaximumPoolSize(masterDataSourceProperties.getMaximumPoolSize());
        return dataSource;
    }

    @Bean("petdiarySlaveDataSource")
    public DataSource slaveDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(slaveDataSourceProperties.getJdbcUrl());
        dataSource.setUsername(slaveDataSourceProperties.getUsername());
        dataSource.setPassword(slaveDataSourceProperties.getPassword());
        dataSource.setDriverClassName(slaveDataSourceProperties.getDriverClassName());
        dataSource.setMaximumPoolSize(slaveDataSourceProperties.getMaximumPoolSize());
        return dataSource;
    }

    @Bean("petdiaryRoutingDataSource")
    public DataSource routingDataSource(
            @Qualifier("petdiaryMasterDataSource") DataSource masterDataSource,
            @Qualifier("petdiarySlaveDataSource") DataSource slaveDataSource
    ) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        routingDataSource.setMasterSlave(masterDataSource, slaveDataSource);
        return routingDataSource;
    }

    @Primary
    @Bean("petdiaryDataSource")
    public DataSource petdiaryDataSource(@Qualifier("petdiaryRoutingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Primary
    @Bean("petdiaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean EntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("petdiaryDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.petdiary.domain.rdspetdiarydb.domain")
                .persistenceUnit("petdiaryEntityManager")
                .build();
    }

    @Primary
    @Bean("petdiaryTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("petdiaryEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
