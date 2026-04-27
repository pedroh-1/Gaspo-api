package com.gaspo.api.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import javax.sql.DataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.gaspo.api.repository.gaspo",
        entityManagerFactoryRef = "gaspoEntityManagerFactory",
        transactionManagerRef = "gaspoTransactionManager"
)
public class GaspoConfig {

    @Bean
    @ConfigurationProperties("app.datasource.gaspo")
    public DataSourceProperties gaspoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "gaspoDataSource")
    public DataSource gaspoDataSource() {
        return gaspoDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "gaspoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean gaspoEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("gaspoDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.gaspo.api.model.gaspo")
                .persistenceUnit("gaspo")
                .build();
    }

    @Bean(name = "gaspoTransactionManager")
    public PlatformTransactionManager gaspoTransactionManager(
            @Qualifier("gaspoEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}