package com.example.second.Repository;

import com.example.second.Entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByCompleted(boolean completed);
    List<TaskEntity> findByUser_Id(Long userId);
}
