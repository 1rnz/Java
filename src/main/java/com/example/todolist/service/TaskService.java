package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Додати нову задачу
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    // Отримати всі задачі для користувача
    public List<Task> getAllTasks(String userId) {
        return taskRepository.findByUserId(userId);
    }

    // Пошук задач за заголовком або описом
    public List<Task> searchTasks(String query, String userId) {
        List<Task> byTitle = taskRepository.findByTitleContainingIgnoreCaseAndUserId(query, userId);
        List<Task> byDescription = taskRepository.findByDescriptionContainingIgnoreCaseAndUserId(query, userId);

        Set<Task> uniqueTasks = new HashSet<>(byTitle);
        uniqueTasks.addAll(byDescription);
        return List.copyOf(uniqueTasks);
    }

    // Оновлення задачі
    public Task updateTask(String id, Task updatedTask) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent() && existingTask.get().getUserId().equals(updatedTask.getUserId())) {
            Task task = existingTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setCompleted(updatedTask.isCompleted());
            task.setStatus(updatedTask.getStatus());
            return taskRepository.save(task);
        }
        return null;
    }

    // Видалення задачі
    public void deleteTask(String id, String userId) {
        Optional<Task> task = taskRepository.findById(id);
        task.ifPresent(t -> {
            if (t.getUserId().equals(userId)) {
                taskRepository.deleteById(id);
            }
        });
    }

    // Отримати задачу за ID
    public Task getTaskById(String id, String userId) {
        Optional<Task> task = taskRepository.findById(id);
        return task.filter(t -> t.getUserId().equals(userId)).orElse(null);
    }
}
