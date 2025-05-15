package com.example.todolist.controller;

import jakarta.servlet.http.HttpSession;
import com.example.todolist.model.Task;
import com.example.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Отримати всі задачі користувача
    @GetMapping
    public String getAllTasks(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        List<Task> tasks = taskService.getAllTasks(userId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("username", session.getAttribute("username"));

        return "tasks"; // шаблон HTML
    }

    // Форма додавання задачі
    @GetMapping("/add")
    public String addTaskForm(Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/user/login";

        model.addAttribute("task", new Task());
        return "add-task";
    }

    // Додавання задачі
    @PostMapping
    public String addTask(@ModelAttribute Task task, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        task.setUserId(userId);
        taskService.addTask(task);
        return "redirect:/tasks";
    }

    // Пошук задач
    @GetMapping("/search")
    public String searchTasks(@RequestParam(name = "query", required = false) String query, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        List<Task> tasks = (query == null || query.trim().isEmpty())
                ? taskService.getAllTasks(userId)
                : taskService.searchTasks(query, userId);

        model.addAttribute("tasks", tasks);
        model.addAttribute("username", session.getAttribute("username"));

        return "tasks";
    }

    // Перегляд задачі
    @GetMapping("/view/{id}")
    public String viewTask(@PathVariable("id") String id, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        Task task = taskService.getTaskById(id, userId);
        if (task == null) return "redirect:/tasks";

        model.addAttribute("task", task);
        model.addAttribute("username", session.getAttribute("username"));

        return "view-task";
    }

    // Форма редагування задачі
    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable("id") String id, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        Task task = taskService.getTaskById(id, userId);
        if (task == null) return "redirect:/tasks";

        model.addAttribute("task", task);
        model.addAttribute("username", session.getAttribute("username"));

        return "edit-task";
    }

    // Редагування задачі
    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable("id") String id, @ModelAttribute Task task, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        task.setUserId(userId);
        taskService.updateTask(id, task);

        return "redirect:/tasks";
    }

    // Видалення задачі
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") String id, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        taskService.deleteTask(id, userId);
        return "redirect:/tasks";
    }
}
