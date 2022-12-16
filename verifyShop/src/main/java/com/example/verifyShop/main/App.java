package com.example.verifyShop.main;

import com.example.verifyShop.configurations.AthenaClientFactory;
import com.example.verifyShop.constants.Constants;
import com.example.verifyShop.services.AthenaOrchestrator;
import com.example.verifyShop.services.AthenaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.services.athena.AthenaClient;

@SpringBootApplication
public class App implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private String defaultQuery = Constants.ATHENA_SAMPLE_QUERY;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        AthenaClientFactory factory = new AthenaClientFactory();
        AthenaClient athenaClient = factory.createClientDev();
        AthenaService athenaQueryExecutor = new AthenaService(athenaClient);
        logger.info("Initializing Athena Orchestrator");

        AthenaOrchestrator orchestrator = new AthenaOrchestrator(defaultQuery, athenaQueryExecutor);
        logger.info("Executing Athena Orchestrator");
        orchestrator.execute();
    }
}