package com.sardor.unsplash.repository;

import com.sardor.unsplash.entity.RequestBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestBookRepository extends JpaRepository<RequestBook,Integer> {
}
