package com.sardor.unsplash.repository;

import com.sardor.unsplash.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
