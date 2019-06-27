package com.java12.service;

import com.google.gson.Gson;
import com.java12.ConstUrls;
import com.java12.entity.pet.Pet;
import com.java12.entity.pet.Status;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PetService implements ConstUrls {

    private Gson jsonParser = new Gson();

    /* methods for user*/
    public String getPetById(int petId) throws IOException {
        String answer = null;
        try {
            answer = Jsoup.
                    connect(baseUrl + basePetUrl + "/" + petId).
                    ignoreContentType(true).method(Connection.Method.GET).
                    execute().body();

        } catch (HttpStatusException exception) {
            answer = catchStatusFormatException(exception, "ID");
        }

        return answer;
    }

    public String postPetToStore(Pet pet) throws IOException {
        return postAndPutMethodForPet(jsonParser.toJson(pet), Connection.Method.POST);
    }

    public String putPetToStore(Pet pet) throws IOException {
        return postAndPutMethodForPet
                (jsonParser.toJson(pet), Connection.Method.PUT);
    }

    public String getPetsByStatus(Status status) throws IOException {
        String response = null;
        try {
            System.out.println(status);
            response = Jsoup.connect(baseUrl + findByStatusUrl + status).ignoreContentType(true).get().text();
        } catch (HttpStatusException e) {
            response = catchStatusFormatException(e, "status");
        }
        return response;
    }

    public String updatePetByIdStatusName(Pet pet) throws IOException {
        try {
            Jsoup.connect(baseUrl + basePetUrl + "/" + pet.getId()).ignoreContentType(true).data("name", pet.getName())
                    .data("status", pet.getStatus().name()).post();
        } catch (HttpStatusException e) {
            return catchStatusFormatException(e, "");
        }
        return infoType + "pet number " + pet.getId() + " | " + "name = " + pet.getName() + " | " + "status = " + pet.getStatus() + "was updated";
    }

    public String deleteById(int petId) throws IOException {
        try {
            Jsoup.connect(baseUrl + basePetUrl + "/" + petId).
                    header("accept", "application/xml").
                    header("api_key", "special-key").
                    method(Connection.Method.DELETE).execute();

        } catch (HttpStatusException e) {
            return catchStatusFormatException(e, "ID");
        }
        return infoType + "pet with id " + petId + "have been deleted";
    }

    public String uploadImage(int petId, File file, String format) throws IOException {
        try {
            Jsoup.connect(baseUrl + basePetUrl + "/" + petId + uploadImageUrl).
                    ignoreContentType(true).header("accept", "application/json").
                    header("Content-Type", "multipart/form-data").data("additionalMetadata", format)
                    .data("file", file.getName(), new FileInputStream(file)).post();
        } catch (HttpStatusException e) {
            return catchStatusFormatException(e, "");
        }
        return infoType + "your file" + file.getName() + " is uploaded";
    }

    /* methods for service*/

    private String postAndPutMethodForPet(String petJson, Connection.Method method) throws IOException {
        String answer = null;
        try {
            Jsoup.connect(baseUrl + basePetUrl).
                    header("accept", "application/xml")
                    .header("Content-Type", "application/json").
                    requestBody(petJson).method(method).execute();
            answer = infoType + method.name();
        } catch (HttpStatusException e) {
            String extraInfo;
            if (method.equals(Connection.Method.POST)) extraInfo = "Invalid input";
            else {
                extraInfo = "Validation exception";
            }

            answer = catchStatusFormatException(e, extraInfo);
        }
        return answer;
    }

    public String catchStatusFormatException(HttpStatusException exception, String extraInfo) {
        switch (exception.getStatusCode()) {
            case 400:
                return errorType + "Invalid" + extraInfo + "supplied";

            case 404:
                return errorType + "pet not found";

            case 405:
                return errorType + extraInfo;

            default:
                return errorType + exception.getStatusCode() + "| " + exception.getMessage();
        }
    }

}
