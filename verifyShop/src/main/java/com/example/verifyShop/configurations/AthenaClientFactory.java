package com.example.verifyShop.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.athena.AthenaClient;

public class AthenaClientFactory {
    @Bean
    @Profile("dev")
    public AthenaClient createClientDev() {
        return AthenaClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create()).build();
    }
}
