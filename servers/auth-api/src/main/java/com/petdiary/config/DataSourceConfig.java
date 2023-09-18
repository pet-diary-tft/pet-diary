package com.petdiary.config;

import com.petdiary.domain.rdscore.repository.ExtendedRepositoryImpl;
import jakarta.persistence.EntityManagerFactory;
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
        entityManagerFactoryRef = "petDiaryMembershipEntityManagerFactory",
        transactionManagerRef = "petDiaryMembershipTransactionManager",
        basePackages = {"com.petdiary.domain.rdspetdiarymembershipdb.repository"}
)
public class DataSourceConfig {
    /* Master-Slave 구조
    @Primary
    @Bean("petDiaryMembershipDataSource")
    public DataSource petdiaryDataSource(@Qualifier("petDiaryMembershipRoutingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
    */

    // Standalone 구조
    @Primary
    @Bean("petDiaryMembershipDataSource")
    public DataSource petdiaryDataSource(@Qualifier("petDiaryMembershipMasterDataSource") DataSource masterDataSource) {
        return masterDataSource;
    }

    @Primary
    @Bean("petDiaryMembershipEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean EntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("petDiaryMembershipDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.petdiary.domain.rdspetdiarymembershipdb.domain")
                .persistenceUnit("petDiaryMembershipEntityManager")
                .build();
    }

    @Primary
    @Bean("petDiaryMembershipTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("petDiaryMembershipEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
