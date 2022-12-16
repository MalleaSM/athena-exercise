package com.example.verifyShop.services;

import com.example.verifyShop.models.Transaction;

import java.util.List;

public interface IAthenaService {
    String submitQuery(String myQuery);
    void waitForQueryToComplete(String queryExecutionId);
    List<Transaction> processQueryResult(String queryExecutionId);
}
