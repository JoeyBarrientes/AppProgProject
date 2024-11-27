package edu.utsa.cs3443.project.controller;

import edu.utsa.cs3443.project.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskController {

    private final List<Task> taskList;

    // Constructor to initialize or load tasks
    public TaskController() {
        taskList = new ArrayList<>();
        // Adding sample tasks for testing
        taskList.add(new Task("2024-11-27", "Complete project report", true));
        taskList.add(new Task("2024-11-27", "Prepare presentation", false));
        taskList.add(new Task("2024-11-15", "Submit homework", true));
        taskList.add(new Task("2024-11-01", "Start coding assignment", false));
        taskList.add(new Task("2024-10-20", "Draft research proposal", true));
        taskList.add(new Task("2024-01-15", "New Year resolution setup", true));
    }

    // Fetch all tasks
    public List<Task> fetchTasks() {
        return taskList;
    }

    // Filter tasks by a specific date
    public List<Task> getTasksByDate(String date) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getDate().equals(date)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    // Filter tasks by a specific month (e.g., "2024-11")
    public List<Task> getTasksByMonth(String month) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getDate().startsWith(month)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    // Filter tasks by a specific year (e.g., "2024")
    public List<Task> getTasksByYear(String year) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getDate().startsWith(year)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    // Calculate the number of completed tasks for a specific date
    public int calculateCompletedTasks(String date) {
        int completed = 0;
        for (Task task : taskList) {
            if (task.getDate().equals(date) && task.getCompleted()) {
                completed++;
            }
        }
        return completed;
    }
}
