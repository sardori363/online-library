package com.sardor.unsplash.entity;

import com.sardor.unsplash.entity.template.AbsEntity;
import com.sardor.unsplash.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book extends AbsEntity {

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(nullable = false)
    private Attachment pdfFile;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @OneToOne
    private Attachment photo;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Date firstPublished;
}
