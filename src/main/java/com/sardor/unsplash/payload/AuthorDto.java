package com.sardor.unsplash.payload;

import lombok.Data;

@Data
public class AuthorDto {
    private String fullname;
    private String dateOfBirthAndDeath;
    private String bio;
}
