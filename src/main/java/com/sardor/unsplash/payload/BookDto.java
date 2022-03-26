package com.sardor.unsplash.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private String name;
    private Integer pdfFileId;
    private String description;
    private Integer categoryId;
    private Integer photoId;
    private Integer authorId;
    private Date firstPublished;
    private Integer addedBy;
    private String language;
}
