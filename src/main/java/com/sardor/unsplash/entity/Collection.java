package com.sardor.unsplash.entity;

import com.sardor.unsplash.entity.template.AbsMainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "collections")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Collection extends AbsMainEntity {
    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Book> books;

    @Column(nullable = false)
    private Boolean isPrivate;
}


