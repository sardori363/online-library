package com.sardor.unsplash.payload;

import lombok.Data;

import java.util.List;

@Data
public class CollectionDto {
    private String name;
    private String description;
    private List<Integer> books;
    private Boolean isPrivate;
}
