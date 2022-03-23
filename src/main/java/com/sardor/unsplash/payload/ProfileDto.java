package com.sardor.unsplash.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String prePassword;
    private String bio;
    private List<Integer> contactsId;
    private Integer photoId;
}
