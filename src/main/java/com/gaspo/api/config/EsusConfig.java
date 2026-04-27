package com.gaspo.api.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.gaspo.api.repository.esus",
        entityManagerFactoryRef = "esusEntityManagerFactory",
        transactionManagerRef = "esusTransactionManager"
)
public class EsusConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties esusDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "esusDataSource")
    public DataSource esusDataSource() {
        return esusDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "esusEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean esusEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("esusDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.gaspo.api.model.esus")
                .persistenceUnit("esus")
                .build();
    }

    @Primary
    @Bean(name = "esusTransactionManager")
    public PlatformTransactionManager esusTransactionManager(
            @Qualifier("esusEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
