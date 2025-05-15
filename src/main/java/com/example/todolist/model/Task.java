package com.example.todolist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class Task {
    @Id
    private String id;

    private String title;        // Назва задачі
    private String description;  // Опис (необов’язково)
    private String userId;       // Користувач (якщо є підтримка багатьох користувачів)
    private boolean completed;   // Чи виконано
    private String priority;     // Наприклад: "low", "medium", "high"
    private String status;       // Наприклад: "active", "archived", "deleted"

    public Task() {}

    public Task(String title, String description, String userId, boolean completed, String priority, String status) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.completed = completed;
        this.priority = priority;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
