package com.sardor.unsplash.repository;

import com.sardor.unsplash.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contacts,Integer> {
}
