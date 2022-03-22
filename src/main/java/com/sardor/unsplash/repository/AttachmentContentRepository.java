package com.sardor.unsplash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sardor.unsplash.entity.AttachmentContent;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent,Integer> {
    Optional<AttachmentContent> findByAttachmentId(Integer id);
}
