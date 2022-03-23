package com.sardor.unsplash.entity;

import com.sardor.unsplash.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AbsEntity {
    @NotNull(message = "Name cannot be null.")
    private String name;
}
