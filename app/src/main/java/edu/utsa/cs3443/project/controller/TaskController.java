package edu.utsa.cs3443.project.controller;

import android.content.Context;
import android.util.Log;

import edu.utsa.cs3443.project.model.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskController {

    private final List<Task> taskList;

    // Constructor to initialize or load tasks
    public TaskController(Context context) {
        taskList = new ArrayList<>();
        // Adding sample tasks for testing
//        taskList.add(new Task("2024-11-27", "Complete project report", true));
//        taskList.add(new Task("2024-11-27", "Prepare presentation", false));
//        taskList.add(new Task("2024-11-15", "Submit homework", true));
//        taskList.add(new Task("2024-11-01", "Start coding assignment", false));
//        taskList.add(new Task("2024-10-20", "Draft research proposal", true));
//        taskList.add(new Task("2024-01-15", "New Year resolution setup", true));
        loadTasksFromCSV(context);
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

    // Check user input for date it proper format (yyyy-mm-dd)
    public static boolean isValidDateFormat(String date){
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public static void writeTaskToCSV(Context context, String date, String description){
        String filepath = context.getFilesDir().getPath() + "/tasks.csv";
        try (FileWriter writer = new FileWriter(filepath, true)){
            String lineToWrite = String.format("%s,%s,false\n", date, description);
            writer.write(lineToWrite);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void loadTasksFromCSV(Context context) {
        File csvFile = new File(context.getFilesDir(), "tasks.csv");

        if (!csvFile.exists()) {
            Log.e("TaskController", "CSV file not found: " + csvFile.getAbsolutePath());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String date = data[0];
                    String description = data[1];
                    boolean isCompleted = Boolean.parseBoolean(data[2]);
                    taskList.add(new Task(date, description, isCompleted));
                }
            }
        } catch (Exception e) {
            Log.e("TaskController", "Error reading CSV file", e);
        }
    }
    public void deleteTask(Context context, Task taskToRemove) {
        File csvFile = new File(context.getFilesDir(), "tasks.csv");
        List<Task> updatedTasks = new ArrayList<>();

        // Read all tasks except the one to delete
        for (Task task : taskList) {
            if (!task.equals(taskToRemove)) {
                updatedTasks.add(task);
            }
        }

        // Write updated tasks back to CSV
        try (FileWriter writer = new FileWriter(csvFile, false)) {
            for (Task task : updatedTasks) {
                writer.write(task.getDate() + "," + task.getDescription() + "," + task.getCompleted() + "\n");
            }
            taskList.clear();
            taskList.addAll(updatedTasks); // Update the in-memory list
        } catch (Exception e) {
            Log.e("TaskController", "Error deleting task from CSV", e);
        }
    }

    public void updateTaskCompletion(Context context, Task updatedTask) {
        File csvFile = new File(context.getFilesDir(), "tasks.csv");
        List<Task> updatedTasks = new ArrayList<>();

        // Update in-memory list and rebuild CSV
        for (Task task : taskList) {
            if (task.getDescription().equals(updatedTask.getDescription()) &&
                    task.getDate().equals(updatedTask.getDate())) {
                task.setCompleted(updatedTask.getCompleted()); // Update completion
            }
            updatedTasks.add(task);
        }

        // Write updated tasks back to CSV
        try (FileWriter writer = new FileWriter(csvFile, false)) {
            for (Task task : updatedTasks) {
                writer.write(task.getDate() + "," + task.getDescription() + "," + task.getCompleted() + "\n");
            }
        } catch (Exception e) {
            Log.e("TaskController", "Error updating task completion in CSV", e);
        }
    }


}
