package edu.utsa.cs3443.project.controller;

import android.content.Context;
import android.util.Log;
import edu.utsa.cs3443.project.model.Task;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskController manages task operations, including fetching tasks,
 * filtering them by date, and updating or deleting tasks in the CSV file.
 */
public class TaskController {

    private final List<Task> taskList;

    /**
     * Constructs a new TaskController and initializes tasks from the CSV file.
     *
     * @param context The application context for file operations.
     */
    public TaskController(Context context) {
        taskList = new ArrayList<>();
        initializePredefinedTasks(context);
        loadTasksFromCSV(context);
    }



    /**
     * Fetches all tasks from the CSV file.
     *
     * @return A list of all tasks.
     */
    public List<Task> fetchTasks() {
        return taskList;
    }

    /**
     * Filters tasks by a specific date.
     *
     * @param date The date to filter tasks by (format: yyyy-MM-dd).
     * @return A list of tasks for the specified date.
     */
    public List<Task> getTasksByDate(String date) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getDate().equals(date)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    /**
     * Filters tasks by a specific month.
     *
     * @param month The month to filter tasks by (format: yyyy-MM).
     * @return A list of tasks for the specified month.
     */
    public List<Task> getTasksByMonth(String month) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getDate().startsWith(month)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    /**
     * Filters tasks by a specific year.
     *
     * @param year The year to filter tasks by (format: yyyy).
     * @return A list of tasks for the specified year.
     */
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

    /**
     * Checks if a date is in a valid format (yyyy-MM-dd).
     *
     * @param date The date string to validate.
     * @return True if the date is valid, false otherwise.
     */
    public static boolean isValidDateFormat(String date){
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    /**
     * Writes a new task to the CSV file.
     *
     * @param context     The application context for file operations.
     * @param date        The task's due date.
     * @param description The task's description.
     */
    public static void writeTaskToCSV(Context context, String date, String description){
        String filepath = context.getFilesDir().getPath() + "/tasks.csv";
        try (FileWriter writer = new FileWriter(filepath, true)){
            String lineToWrite = String.format("%s,%s,false\n", date, description);
            writer.write(lineToWrite);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Loads all tasks from the CSV file.
     *
     * @param context     The application context for file operations.
     */
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

    /**
     * Deletes a task from the CSV file.
     *
     * @param context   The application context for file operations.
     * @param taskToRemove The task to delete.
     */
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

    /**
     * Updates the completion status of a task in the CSV file.
     *
     * @param context   The application context for file operations.
     * @param updatedTask The task to update.
     */
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
    /**
     * Initializes predefined tasks in the CSV file if it is empty or the first time the app runs.
     *
     * @param context The application context for file operations.
     */
    private void initializePredefinedTasks(Context context) {
        File csvFile = new File(context.getFilesDir(), "tasks.csv");

        // Check if the file is empty
        if (csvFile.length() == 0) {
            writePredefinedTasksToCSV(context);
        }
    }

    /**
     * Writes a set of predefined tasks to the CSV file.
     *
     * @param context The application context for file operations.
     */
    private void writePredefinedTasksToCSV(Context context) {
        try (FileWriter writer = new FileWriter(new File(context.getFilesDir(), "tasks.csv"), true)) {
            // Predefined tasks
            writer.write("2024-11-15,Complete project report,true\n");
            writer.write("2024-11-27,Prepare presentation,true\n");
            writer.write("2024-12-02,Start coding assignment,false\n");
            writer.write("2024-12-06,Update resume,false\n");
            writer.write("2024-12-25,Hello World!,true\n");
        } catch (IOException e) {
            Log.e("TaskController", "Error initializing predefined tasks", e);
        }
    }


}
