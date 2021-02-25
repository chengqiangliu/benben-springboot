package com.benben.dao.config;

import com.benben.logging.Loggers;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages= { "com.benben.dao" })
@EnableTransactionManagement
public class MySqlConfig {

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.validationQuery:SELECT 1}")
    private String validationQuery;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis:300000}")
    private int timeBetweenEvictionRunsMillis;

    @Bean
    public DataSource createBmsDataSource() {
        DataSourceBuilder factory = DataSourceBuilder
                .create()
                .driverClassName(this.driverClassName)
                .url(this.url)
                .username(this.username)
                .password(this.password);
        org.apache.tomcat.jdbc.pool.DataSource tomcatDataSource = (org.apache.tomcat.jdbc.pool.DataSource) factory
                .build();
        tomcatDataSource.setTestOnBorrow(true);
        tomcatDataSource.setTestOnConnect(true);
        tomcatDataSource.setValidationQuery(this.validationQuery);
        if (this.timeBetweenEvictionRunsMillis > 0) {
            tomcatDataSource.setTestWhileIdle(true);
            tomcatDataSource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        }
        Loggers.DAO_LOGGER.info("Bms DataSource {} {}", tomcatDataSource.getDriverClassName(), tomcatDataSource.getUrl());
        return tomcatDataSource;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    @Autowired
    public EntityManagerFactory entityManagerFactory(DataSource bmsDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(bmsDataSource);
        em.setPackagesToScan("com.benben.dao.entities");
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        em.setJpaProperties(hibernateProperties);
        em.afterPropertiesSet();
        EntityManagerFactory factory = em.getObject();
        return factory;
    }
}
