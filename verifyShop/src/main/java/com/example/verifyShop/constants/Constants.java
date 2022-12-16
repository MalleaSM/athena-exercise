package com.example.verifyShop.constants;

public interface Constants {
    String ATHENA_OUTPUT_BUCKET = "s3://re-athena-s3/results/";
    String ATHENA_SAMPLE_QUERY = "SELECT * FROM products;";
    int TIMEOUT = 100000;
    long SLEEP_AMOUNT_IN_MS = 1000;
    String ATHENA_DEFAULT_DATABASE = "devopsathenadb";
}
