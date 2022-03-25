package com.sardor.unsplash.payload;

import com.sardor.unsplash.entity.Author;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Data
public class ConfirmDto {
    boolean bool;

    private Integer authorId;
}
