package com.sardor.unsplash.repository;

import com.sardor.unsplash.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
}
