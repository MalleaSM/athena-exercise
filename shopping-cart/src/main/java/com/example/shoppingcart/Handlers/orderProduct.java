package com.example.shoppingcart.Handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.shoppingcart.Model.ProductData;
import com.google.gson.Gson;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

import java.nio.charset.StandardCharsets;

public class orderProduct implements RequestHandler<Object, Object> {

    private static final String SECOND_FUNCTION_NAME = "SMM-verify-product";
    private final LambdaClient lambdaClient;

    public orderProduct() {
        lambdaClient = DependencyFactory.lambdaClient();
    }

    public orderProduct(LambdaClient lambdaClient) {
        this.lambdaClient = lambdaClient;

    }

    @Override
    public Object handleRequest(Object input, Context context) {
        //        var payload = SdkBytes.fromUtf8String("{\"input\":\"bootcamp\"}");
        var payloadData = new ProductData();
        payloadData.setId("55e4814d-0175-464b-944f-3ab9182f3ed4");
        payloadData.setName("pipoca");
        payloadData.setStock(1);

        //This will convert object to JSON String

        String inputJSON = new Gson().toJson(payloadData);
        System.out.println(inputJSON);
        SdkBytes payload = SdkBytes.fromUtf8String(inputJSON);
        System.out.println(payload);

        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName(SECOND_FUNCTION_NAME)
                .invocationType(InvocationType.EVENT)
                .payload(payload)
                .build();

        InvokeResponse response = lambdaClient.invoke(invokeRequest);

        System.out.println("Response: " + response.toString());
        System.out.println("Response status: " + response.statusCode());

        return input;
    }
}
