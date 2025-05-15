package com.example.todolist.repository;

import com.example.todolist.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {

    // Пошук за заголовком та користувачем
    List<Task> findByTitleContainingIgnoreCaseAndUserId(String title, String userId);

    // Пошук за описом та користувачем
    List<Task> findByDescriptionContainingIgnoreCaseAndUserId(String description, String userId);

    // Пошук всіх задач для користувача
    List<Task> findByUserId(String userId);
}
