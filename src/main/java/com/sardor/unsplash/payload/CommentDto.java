package com.sardor.unsplash.payload;

import lombok.Data;

@Data
public class CommentDto {
    private Integer bookId;
    private String text;
}
