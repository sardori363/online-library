package com.sardor.unsplash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sardor.unsplash.entity.Attachment;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {

    Optional<Attachment> findByName(String name);
}
