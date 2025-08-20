package com.example.second.Repository;

import com.example.second.Entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @EntityGraph(attributePaths = "tasks")
    List<UserEntity> findAll();

    @EntityGraph(attributePaths = "tasks")
    List<UserEntity> findAllByOrderByIdAsc();
}
