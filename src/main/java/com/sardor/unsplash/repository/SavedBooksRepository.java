package com.sardor.unsplash.repository;

import com.sardor.unsplash.entity.SavedBooks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedBooksRepository extends JpaRepository<SavedBooks,Integer> {
}
