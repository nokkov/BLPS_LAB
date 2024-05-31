package ru.nokkov.blps_lab.config;

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

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement

@EnableJpaRepositories(
        basePackages = {"ru.nokkov.blps_lab.article", "ru.nokkov.blps_lab.user"},
        entityManagerFactoryRef = "articleEntityManagerFactory",
        transactionManagerRef = "partnerTxManager"
)
public class ArticleDatasourceConfig {

    @Bean
    @ConfigurationProperties("spring.article.datasource")
    public DataSourceProperties articleDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource articleDataSource() {
        return articleDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean articleEntityManagerFactory (
            @Qualifier("articleDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(dataSource)
                .packages("ru.nokkov.blps_lab.article", "ru.nokkov.blps_lab.user")
                .persistenceUnit("articlePersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "articleTxManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("articleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}