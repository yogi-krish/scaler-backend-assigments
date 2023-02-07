package com.spring.taskmanager;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TaskController {

    private List<Task> taskList;
    private AtomicInteger taskId = new AtomicInteger(0);
    public TaskController() {
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(), "Task 1", "Description 1", new Date()));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 2", "Description 2", new Date()));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 3", "Description 3", new Date()));
    }

    @GetMapping("/tasks")
    public List<Task> getTaskList() {
        return taskList;
    }

    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {
        Task newTask = new Task(taskId.incrementAndGet(), task.getTitle(), task.getDescription(), task.getDueDate());
        taskList.add(newTask);
        return newTask;
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable Integer id) {
        Task addedTask = null;
        for(Task task: taskList) {
            if (task.getId() == id) {
                addedTask = task;
                break;
            }
        }
        if(addedTask == null)
            throw new TaskNotFoundException("ID:" + id);
        return addedTask;
    }

    @DeleteMapping("/tasks/{id}")
    public Task deleteTask(@PathVariable Integer id) {
        Task deletedTask = null;
        for(Integer i=0; i<taskList.size(); i++) {
            if(taskList.get(i).getId() == id) {
                deletedTask = taskList.get(i);
                taskList.remove(i.intValue());
                break;
            }
        }
        if(deletedTask == null)
            throw new TaskNotFoundException("Id:" + id);
        return deletedTask;
    }

    @PatchMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody Task task) {
        Task updatedTask = null;
        for(Task eachTask:taskList) {
            if(eachTask.getId() == id) {
                eachTask.setTitle(task.getTitle());
                eachTask.setDescription(task.getDescription());
                eachTask.setDueDate(task.getDueDate());
                updatedTask = eachTask;
                break;
            }
        }
        if(updatedTask == null)
            throw new TaskNotFoundException("Id: " + id);

        return updatedTask;
    }
}
