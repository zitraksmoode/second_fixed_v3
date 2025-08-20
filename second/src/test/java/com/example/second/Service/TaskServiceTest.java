package com.example.second.Service;

import com.example.second.Entity.TaskEntity;
import com.example.second.Repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
@Mock
private TaskRepository taskRepository;

@InjectMocks
private TaskService taskService;
    @Test
    void findAllTask() {
        TaskEntity task = new TaskEntity();
        task.setTitle("123");
        when(taskRepository.findAll()).thenReturn(List.of(task));
        List<TaskEntity> result = taskService.findAllTask();
        assertEquals(1,result.size());
        assertEquals("123",result.getFirst().getTitle());
    }
}