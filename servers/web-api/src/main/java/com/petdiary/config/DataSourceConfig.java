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
        entityManagerFactoryRef = "petDiaryEntityManagerFactory",
        transactionManagerRef = "petDiaryTransactionManager",
        basePackages = {"com.petdiary.domain.rdspetdiarydb.repository"}
)
public class DataSourceConfig {
    @Primary
    @Bean("petDiaryDataSource")
    public DataSource petdiaryDataSource(@Qualifier("petDiaryRoutingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Primary
    @Bean("petDiaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean EntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("petDiaryDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.petdiary.domain.rdspetdiarydb.domain")
                .persistenceUnit("petDiaryEntityManager")
                .build();
    }

    @Primary
    @Bean("petDiaryTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("petDiaryEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
