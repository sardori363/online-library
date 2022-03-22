package com.sardor.unsplash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sardor.unsplash.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByName(String name);


    Optional<Role> findByName(String name);

}
