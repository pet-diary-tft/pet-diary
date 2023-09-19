package com.petdiary.domain.rdspetdiarymembershipdb.config;

import com.petdiary.domain.rdscore.interfaces.IDomainDataSourceConfig;
import com.petdiary.domain.rdscore.NoOpDataSource;
import com.petdiary.domain.rdscore.ReplicationRoutingDataSource;
import com.petdiary.domain.rdscore.repository.ExtendedRepositoryImpl;
import com.petdiary.domain.rdspetdiarymembershipdb.PetDiaryMembershipConstants;
import com.petdiary.domain.rdspetdiarymembershipdb.properties.PetDiaryMembershipMasterDataSourceProperties;
import com.petdiary.domain.rdspetdiarymembershipdb.properties.PetDiaryMembershipSlaveDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
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

@Configuration("petDiaryMembershipDataSourceConfig")
@EnableTransactionManagement
@EnableJpaRepositories(
        repositoryBaseClass = ExtendedRepositoryImpl.class,
        entityManagerFactoryRef = PetDiaryMembershipConstants.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = PetDiaryMembershipConstants.TRANSACTION_MANAGER,
        basePackages = {"com.petdiary.domain.rdspetdiarymembershipdb.repository"}
)
@RequiredArgsConstructor
public class DomainDataSourceConfig implements IDomainDataSourceConfig {
    private final PetDiaryMembershipMasterDataSourceProperties masterDataSourceProperties;
    private final PetDiaryMembershipSlaveDataSourceProperties slaveDataSourceProperties;

    @Override
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

    @Override
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

    @Override
    @Bean("petDiaryMembershipDataSource")
    public DataSource dataSource(
            @Qualifier("petDiaryMembershipMasterDataSource") DataSource masterDataSource,
            @Qualifier("petDiaryMembershipSlaveDataSource") DataSource slaveDataSource
    ) {
        // Master-Slave
        if (slaveDataSourceProperties.isEnabled()) {
            ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
            routingDataSource.setMasterSlave(masterDataSource, slaveDataSource);
            return new LazyConnectionDataSourceProxy(routingDataSource);
        }
        // Standalone
        else {
            return masterDataSource;
        }
    }

    @Override
    @Bean(PetDiaryMembershipConstants.ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("petDiaryMembershipDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.petdiary.domain.rdspetdiarymembershipdb.domain")
                .persistenceUnit(PetDiaryMembershipConstants.ENTITY_MANAGER)
                .build();
    }

    @Override
    @Bean(PetDiaryMembershipConstants.TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager(
            @Qualifier(PetDiaryMembershipConstants.ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
