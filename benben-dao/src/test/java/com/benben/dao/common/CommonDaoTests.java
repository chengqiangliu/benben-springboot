package com.benben.dao.common;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Ignore
@SpringBootTest(classes = { CommonDaoTests.SpringTestConfig.class })
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "classpath:truncate_tables.sql")
@ActiveProfiles("test")
public class CommonDaoTests {

    @Configuration
    @EnableJpaRepositories(basePackages= { "com.benben.dao" })
    @EntityScan("com.benben.dao.entities")
    @EnableTransactionManagement()
    public static class SpringTestConfig {

    }
}
