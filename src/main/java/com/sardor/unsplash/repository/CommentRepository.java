package com.sardor.unsplash.repository;

import com.sardor.unsplash.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = "select * from comment c where book_id = ?1", nativeQuery = true)
    List<Comment> findAllByBookId(Integer book_id);
}
