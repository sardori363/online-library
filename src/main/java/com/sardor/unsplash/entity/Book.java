package com.sardor.unsplash.entity;

import com.sardor.unsplash.entity.template.AbsEntity;
import com.sardor.unsplash.entity.template.AbsMainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Book extends AbsEntity {

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Attachment pdfFile;

    @Column(length = 5000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Author author;

    @Column(nullable = false)
    private Date firstPublished;

    @ManyToOne(fetch = FetchType.LAZY)
    private User addedBy;
}
