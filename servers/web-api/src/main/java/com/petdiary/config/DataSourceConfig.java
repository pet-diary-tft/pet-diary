package com.petdiary.config;

import com.petdiary.domain.rdscore.DomainCoreConstants;
import com.petdiary.domain.rdscore.interfaces.IAppDataSourceConfig;
import com.petdiary.domain.rdspetdiarydb.PetDiaryConstants;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig implements IAppDataSourceConfig {
    private final JpaProperties jpaProperties;

    @Override
    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaProperties.getProperties(), null);
    }

    @Override
    @Primary
    @Bean(DomainCoreConstants.DEFAULT_TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager(
            @Qualifier(PetDiaryConstants.ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
