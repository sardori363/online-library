package com.sardor.unsplash.entity;

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
@EntityListeners(AuditingEntityListener.class)
public class RequestBook extends AbsMainEntity {
    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Attachment pdfFile;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment photo;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Date firstPublished;

    @Column(nullable = false,length = 5)
    private String language;
}
