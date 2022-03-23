package com.sardor.unsplash.repository;

import com.sardor.unsplash.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
