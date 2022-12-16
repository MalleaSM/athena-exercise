package com.example.shoppingcart.Handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.shoppingcart.Model.ProductData;

public class verifyProduct implements RequestHandler<ProductData, Object> {
    @Override
    public Object handleRequest(ProductData input, Context context) {
        return null;
    }
}
