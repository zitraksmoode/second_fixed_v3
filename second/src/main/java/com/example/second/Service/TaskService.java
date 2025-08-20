package com.example.second.Service;

import com.example.second.Entity.TaskEntity;
import com.example.second.Entity.UserEntity;
import com.example.second.Exception.TaskNotFoundException;
import com.example.second.Repository.TaskRepository;
import com.example.second.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskEntity> findAllTask(){
        return taskRepository.findAll();
    }

    public List<TaskEntity> findAllCompleted(boolean status){
        return taskRepository.findByCompleted(status);
    }

    public TaskEntity findTaskByIdOrThrow(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));
    }

    @Transactional
    public TaskEntity createTask(TaskEntity task){
        // Если пришёл user.id в теле — подхватим/верифицируем
        if (task.getUser() != null && task.getUser().getId() != null) {
            Long userId = task.getUser().getId();
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new TaskNotFoundException("User not found with id " + userId));
            task.setUser(user);
        }
        return taskRepository.save(task);
    }

    @Transactional
    public TaskEntity createTaskForUser(TaskEntity task, Long userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new TaskNotFoundException("User not found with id " + userId));
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Transactional
    public TaskEntity updateTask(TaskEntity taskFromRequest, Long id){
        TaskEntity existing = findTaskByIdOrThrow(id);
        if (taskFromRequest.getTitle() != null) existing.setTitle(taskFromRequest.getTitle());
        if (taskFromRequest.getDescription() != null) existing.setDescription(taskFromRequest.getDescription());
        if (taskFromRequest.getPriority() != null) existing.setPriority(taskFromRequest.getPriority());
        existing.setCompleted(taskFromRequest.isCompleted());

        // Перекидывать задачу на другого пользователя, если передали user.id
        if (taskFromRequest.getUser() != null && taskFromRequest.getUser().getId() != null) {
            Long newUserId = taskFromRequest.getUser().getId();
            UserEntity user = userRepository.findById(newUserId)
                    .orElseThrow(() -> new TaskNotFoundException("User not found with id " + newUserId));
            existing.setUser(user);
        }
        return taskRepository.save(existing);
    }

    public void deleteTask(Long id){
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }
}
