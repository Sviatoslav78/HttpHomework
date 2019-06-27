package com.java12;

import org.jsoup.HttpStatusException;

public interface ConstUrls {
    String baseUrl ="http://petstore.swagger.io/v2";
    String basePetUrl = "/pet";
    String findByStatusUrl = "/pet/findByStatus?status=";
    String uploadImageUrl = "/uploadImage";

    String errorType = "Error type is: ";
    String infoType = "Method executed successful: ";

    String baseStoreUrl = "/store";
    String placeOrderUrl = "/order";
    String getInventoryUrl = "/inventory";
    String orderByIdUrl = "/order";

    String catchStatusFormatException(HttpStatusException exception, String extraInfo);
}
