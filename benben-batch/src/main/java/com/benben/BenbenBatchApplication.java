package com.benben;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@SpringBootApplication
public class BenbenBatchApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BenbenBatchApplication.class);

        /* Stop tomcat service */
        app.setWebEnvironment(false);
        app.run(args).close();
    }
}
