package com.petdiary.domain.rdscore.interfaces;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

public interface IDomainStandaloneDataSourceConfig {
    DataSource dataSource();
    LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource);
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory);
}
