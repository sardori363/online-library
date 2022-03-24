package com.sardor.unsplash.payload;

import lombok.Data;

import java.sql.Date;

@Data
public class RequestDto {
    private String name;
    private Integer pdfFileId;
    private String description;
    private Integer categoryId;
    private Integer photoId;
    private String author;
    private Date firstPublished;
}
