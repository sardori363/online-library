package com.sardor.unsplash.repository;

import com.sardor.unsplash.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
