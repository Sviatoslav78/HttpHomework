package com.java12.service;

import com.google.gson.Gson;
import com.java12.ConstUrls;
import com.java12.entity.order.Order;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import java.io.IOException;

public class OrderService implements ConstUrls {

    private Gson jsonParser = new Gson();

    public String addOrderToPet(Order order) throws IOException {
        try {
            Jsoup.connect(baseUrl + baseStoreUrl + placeOrderUrl).
                    header("accept", "application/xml").
                    header("Content-Type", "application/json").
                    requestBody(jsonParser.toJson(order)).ignoreContentType(true).post();
        } catch (HttpStatusException e) {
            return catchStatusFormatException(e, "Order");
        }
        return infoType + "Order number " + order.getId() + " was added";
    }

    public String getPetInventory() throws IOException {
        try {
            return Jsoup.connect(baseUrl + baseStoreUrl + getInventoryUrl).
                    header("accept", "application/json").get().text();
        } catch (HttpStatusException e) {
            return catchStatusFormatException(e, "");
        }
    }

    public String findPurchaseById(int orderId) throws IOException {
        try {
            return Jsoup.connect(baseUrl + baseStoreUrl + orderByIdUrl + "/" + orderId).
                    header("accept", "application/json").
                    method(Connection.Method.GET).
                    execute().body();

        } catch (HttpStatusException e) {
            return catchStatusFormatException(e, "ID supplied");
        }
    }

    public String deleteOrderById(int orderId) throws IOException {
        try {
            Jsoup.connect(baseUrl + baseStoreUrl + orderByIdUrl + "/" + orderId).
                    header("accept", "application/xml").
                    method(Connection.Method.DELETE).execute().body();

        } catch (HttpStatusException e) {
            return catchStatusFormatException(e, "ID supplied");
        }
        return infoType + "Order number number " + orderId + "was deleted";
    }

    @Override
    public String catchStatusFormatException(HttpStatusException exception, String extraInfo) {
        switch (exception.getStatusCode()) {
            case 400:
                return errorType + "Invalid " + extraInfo;

            case 404:
                return errorType + "Order not found";

            default:
                return errorType + exception.getStatusCode() + "| " + exception.getMessage();
        }


    }

}
