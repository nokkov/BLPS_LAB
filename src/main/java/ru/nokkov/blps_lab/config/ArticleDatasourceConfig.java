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
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement

@EnableJpaRepositories(
        basePackages = {"ru.nokkov.blps_lab.article", "ru.nokkov.blps_lab.user"},
        entityManagerFactoryRef = "articleEntityManagerFactory"
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

//    @Bean(name = "txManager")
//    @Primary
//    public PlatformTransactionManager transactionManager() throws NamingException {
//        InitialContext context = new InitialContext();
//        UserTransaction ut = (UserTransaction) context.lookup("java:comp/UserTransaction");
//        TransactionManager tm = (TransactionManager) context.lookup("java:comp/TransactionManager");
//        return new JtaTransactionManager(ut, tm);
//    }
}