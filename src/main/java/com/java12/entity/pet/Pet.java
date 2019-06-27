package com.java12.entity.pet;


import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

@Data
@Builder
public class Pet {
    private long id;
    private String name;
    private Status status;
    private Category category;
    private Tag[] tags;
    private String[] photoUrls;

    @Override
    public String toString() {
        return "PET:\n" +
                "ID:" + id + ";\n" +
                "NAME: " + name + ";\n" +
                "STATUS: " + status + ";\n" +
                "CATEGORY: " + category + ";\n" +
                "TAGS: " + Arrays.toString(tags) + ";\n" +
                "PHOTO-URLS: " + Arrays.toString(photoUrls) + ";";
    }

}

