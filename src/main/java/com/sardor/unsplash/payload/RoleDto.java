package com.sardor.unsplash.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.sardor.unsplash.enums.Permissions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    @NotBlank
    private String name;

    @NotEmpty
    private List<Permissions> permissions;

    private String description;

    private Integer businessId;
}
