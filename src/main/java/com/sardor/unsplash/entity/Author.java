package com.sardor.unsplash.entity;

import com.sardor.unsplash.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Author extends AbsEntity {
    @Column(nullable = false)
    private String fullname;
    @Column(nullable = false)
    private String dateOfBirthAndDeath;
    @Column(nullable = false)
    private String bio;
}
