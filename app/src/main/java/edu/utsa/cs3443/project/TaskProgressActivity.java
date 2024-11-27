package edu.utsa.cs3443.project;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.project.controller.TaskController;
import edu.utsa.cs3443.project.model.Task;

import java.time.LocalDate;
import java.util.List;

public class TaskProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_progress);

        // Initialize TaskController
        TaskController taskController = new TaskController();

        // Dynamically get current date
        LocalDate currentDate = LocalDate.now();
        String date = currentDate.toString(); // Format: "yyyy-MM-dd"
        String month = date.substring(0, 7);  // Format: "yyyy-MM"
        String year = date.substring(0, 4);   // Format: "yyyy"

        // Daily Progress
        List<Task> dailyTasks = taskController.getTasksByDate(date);
        int completedDailyTasks = taskController.calculateCompletedTasks(date);

        ProgressBar dailyProgressBar = findViewById(R.id.dailyProgressBar);
        if (!dailyTasks.isEmpty()) {
            dailyProgressBar.setProgress((completedDailyTasks * 100) / dailyTasks.size());
        }

        // Monthly Progress
        List<Task> monthlyTasks = taskController.getTasksByMonth(month);
        int completedMonthlyTasks = (int) monthlyTasks.stream().filter(Task::getCompleted).count();

        ProgressBar monthlyProgressBar = findViewById(R.id.monthlyProgressBar);
        if (!monthlyTasks.isEmpty()) {
            monthlyProgressBar.setProgress((completedMonthlyTasks * 100) / monthlyTasks.size());
        }

        // Yearly Progress
        List<Task> yearlyTasks = taskController.getTasksByYear(year);
        int completedYearlyTasks = (int) yearlyTasks.stream().filter(Task::getCompleted).count();

        ProgressBar yearlyProgressBar = findViewById(R.id.yearlyProgressBar);
        if (!yearlyTasks.isEmpty()) {
            yearlyProgressBar.setProgress((completedYearlyTasks * 100) / yearlyTasks.size());
        }
    }
}
