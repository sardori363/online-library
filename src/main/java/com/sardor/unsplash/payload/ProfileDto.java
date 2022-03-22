package com.sardor.unsplash.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    @NotNull(message = "required line")
    private String firstName;
    @NotNull(message = "required line")
    private String lastName;
    @NotNull(message = "required line")
    private String username;
    @NotNull(message = "required line")
    private String password;
    @NotNull(message = "required line")
    private String prePassword;

    public ProfileDto(String firstName, String lastName, String username, String password, String prePassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.prePassword = prePassword;
    }

    private Integer photoId;
}
