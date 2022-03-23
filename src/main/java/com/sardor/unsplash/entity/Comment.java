package com.sardor.unsplash.entity;

import com.sardor.unsplash.entity.template.AbsEntity;
import com.sardor.unsplash.entity.template.AbsMainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Comment extends AbsMainEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Book book;

    @Column(length = 500)
    private String text;

}
