package com.sardor.unsplash.repository;

import com.sardor.unsplash.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection,Integer> {
}
