package com.gaspo.api.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import jakarta.sql.DataSource;

@Configuration
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

    @Bean
    public DataSource gaspoDataSource() {
        return gaspoDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean gaspoEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(gaspoDataSource())
                .packages("com.gaspo.api.model.gaspo")
                .persistenceUnit("gaspo")
                .build();
    }

    @Bean
    public JpaTransactionManager gaspoTransactionManager(LocalContainerEntityManagerFactoryBean gaspoEntityManagerFactory) {
        return new JpaTransactionManager(gaspoEntityManagerFactory.getObject());
    }
}