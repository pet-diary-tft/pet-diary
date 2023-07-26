package com.petdiary.domain.rdspetdiarymembershipdb.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("pet-diary-membership.slave.datasource")
@Getter @Setter
public class PetDiaryMembershipSlaveDataSourceProperties {
    private String jdbcUrl;
    private String username;
    private String password;
    private String driverClassName;
    private int maximumPoolSize;
}
