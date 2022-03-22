package com.sardor.unsplash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sardor.unsplash.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, Integer id);
    List<User> findAllByRole_Id(Integer role_id);



}