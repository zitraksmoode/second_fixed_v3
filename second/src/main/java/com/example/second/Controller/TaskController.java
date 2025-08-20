package com.example.second.Controller;

import com.example.second.Entity.TaskEntity;
import com.example.second.Service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public List<TaskEntity> findAll() {
        log.info("findAll tasks");
        return taskService.findAllTask();
    }

    @GetMapping("/tasks/completed")
    public List<TaskEntity> findAllCompleted(@RequestParam boolean status) {
        log.info("All completed tasks by status {}", status);
        return taskService.findAllCompleted(status);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskEntity> findById(@PathVariable Long id){
        log.info("Find task by Id {}", id);
        TaskEntity task = taskService.findTaskByIdOrThrow(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskEntity> createTask(@RequestBody TaskEntity task){
        TaskEntity created = taskService.createTask(task);
        return ResponseEntity.status(201).body(created);
    }

    @PostMapping("/users/{userId}/tasks")
    public ResponseEntity<TaskEntity> createTaskForUser(@RequestBody TaskEntity task, @PathVariable Long userId){
        TaskEntity created = taskService.createTaskForUser(task, userId);
        return ResponseEntity.status(201).body(created);
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<TaskEntity> updateTask(@RequestBody TaskEntity taskFromRequest, @PathVariable Long id) {
        TaskEntity updated = taskService.updateTask(taskFromRequest, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        log.info("Delete task by Id {}", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
