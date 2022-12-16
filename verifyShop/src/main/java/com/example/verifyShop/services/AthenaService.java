package com.example.verifyShop.services;

import com.example.verifyShop.constants.Constants;
import com.example.verifyShop.models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.model.*;
import software.amazon.awssdk.services.athena.paginators.GetQueryResultsIterable;

import java.util.ArrayList;
import java.util.List;

public class AthenaService {
    private static final Logger logger = LoggerFactory.getLogger(AthenaService.class);
    private final AthenaClient athenaClient;

    public AthenaService(AthenaClient athenaClient) {
        this.athenaClient = athenaClient;
    }

    public String submitQuery(String query) {

        QueryExecutionContext queryExecutionContext = QueryExecutionContext.builder()
                .database(Constants.ATHENA_DEFAULT_DATABASE).build();

        ResultConfiguration resultConfiguration = ResultConfiguration.builder()
                .outputLocation(Constants.ATHENA_OUTPUT_BUCKET).build();

        StartQueryExecutionRequest startQueryExecutionRequest = StartQueryExecutionRequest.builder()
                .queryString(query)
                .queryExecutionContext(queryExecutionContext)
                .resultConfiguration(resultConfiguration).build();

        StartQueryExecutionResponse startQueryExecutionResponse = this.athenaClient.startQueryExecution(startQueryExecutionRequest);

        return startQueryExecutionResponse.queryExecutionId();
    }

    // Wait for an Amazon Athena query to complete, fail or to be cancelled
    public void waitForQueryToComplete(String queryExecutionId) {

        GetQueryExecutionRequest getQueryExecutionRequest = GetQueryExecutionRequest.builder()
                .queryExecutionId(queryExecutionId).build();

        GetQueryExecutionResponse getQueryExecutionResponse;
        boolean isQueryStillRunning = true;
        while (isQueryStillRunning) {
            getQueryExecutionResponse = this.athenaClient.getQueryExecution(getQueryExecutionRequest);
            String queryState = getQueryExecutionResponse.queryExecution().status().state().toString();
            if (queryState.equals(QueryExecutionState.FAILED.toString())) {
                throw new RuntimeException("The Amazon Athena query failed to run with error message: " + getQueryExecutionResponse
                        .queryExecution().status().stateChangeReason());
            } else if (queryState.equals(QueryExecutionState.CANCELLED.toString())) {
                throw new RuntimeException("The Amazon Athena query was cancelled.");
            } else if (queryState.equals(QueryExecutionState.SUCCEEDED.toString())) {
                isQueryStillRunning = false;
            } else {
                // Sleep an amount of time before retrying again
                try {
                    Thread.sleep(Constants.SLEEP_AMOUNT_IN_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            logger.debug("The current status is: " + queryState);
        }
    }

    public List<Transaction> processQueryResult(String queryExecutionId) {
        List<Transaction> transactionList = new ArrayList<>();
        List<Row> rows;

        try {
            GetQueryResultsRequest getQueryResultsRequest = GetQueryResultsRequest.builder()
                    .queryExecutionId(queryExecutionId).build();

            GetQueryResultsIterable getQueryResultsResults = this.athenaClient.getQueryResultsPaginator(getQueryResultsRequest);

            for (GetQueryResultsResponse result : getQueryResultsResults) {
                rows = result.resultSet().rows();

                for (Row myRow : rows.subList(1, rows.size())) { // skip first row – column names
                    List<Datum> allData = myRow.data();
                    Transaction transaction = new Transaction();
                    transaction.setId(allData.get(0).varCharValue());
                    transaction.setName(allData.get(1).varCharValue());
                    transaction.setStock(Integer.parseInt(allData.get(2).varCharValue())    );
                    transactionList.add(transaction);
                }
            }
        } catch (AthenaException e) {
            logger.error(e.getMessage());
        }

        return transactionList;
    }
}
