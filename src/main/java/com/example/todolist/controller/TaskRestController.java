package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Task API", description = "REST API для керування задачами")
@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    @Autowired
    private TaskService taskService;

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Operation(summary = "Отримати всі задачі користувача")
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks(getCurrentUserId());
    }

    @Operation(summary = "Отримати задачу за ID")
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id, getCurrentUserId());
    }

    @Operation(summary = "Створити нову задачу")
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setUserId(getCurrentUserId());
        return taskService.addTask(task);
    }

    @Operation(summary = "Оновити задачу")
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task) {
        task.setUserId(getCurrentUserId());
        return taskService.updateTask(id, task);
    }

    @Operation(summary = "Видалити задачу")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id, getCurrentUserId());
    }

    @Operation(summary = "Пошук задач за запитом")
    @GetMapping("/search")
    public List<Task> searchTasks(@RequestParam String query) {
        return taskService.searchTasks(query, getCurrentUserId());
    }
}
