package com.petdiary.domain.rdscore.interfaces;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;

public interface IAppDataSourceConfig {
    EntityManagerFactoryBuilder entityManagerFactoryBuilder();

    /**
     * Default Transactional
     */
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory);
}
