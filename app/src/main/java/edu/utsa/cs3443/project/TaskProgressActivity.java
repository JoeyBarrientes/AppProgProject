package edu.utsa.cs3443.project;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import edu.utsa.cs3443.project.controller.NavigationController;
import edu.utsa.cs3443.project.controller.TaskController;
import edu.utsa.cs3443.project.model.Task;
import java.time.LocalDate;
import java.util.List;

/**
 * TaskProgressActivity displays the progress of task completion in the form of
 * daily, monthly, and yearly progress bars. It also includes motivational flavor
 * texts and animated progress updates for better aesthetics.
 */
public class TaskProgressActivity extends AppCompatActivity {

    private TaskController taskController;

    /** Progress bar displaying daily task completion progress. */
    private ProgressBar dailyProgressBar;

    /** Progress bar displaying monthly task completion progress. */
    private ProgressBar monthlyProgressBar;

    /** Progress bar displaying yearly task completion progress. */
    private ProgressBar yearlyProgressBar;

    /** Flavor text displayed below the daily progress bar. */
    private TextView dailyFlavorText;

    /** Flavor text displayed below the monthly progress bar. */
    private TextView monthlyFlavorText;

    /** Flavor text displayed below the yearly progress bar. */
    private TextView yearlyFlavorText;

    /**
     * Initializes the activity, including navigation buttons, progress bars,
     * flavor texts, and the task controller for fetching task data.
     *
     * @param savedInstanceState The saved instance state for restoring activity state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_progress);

        // Initialize navigation buttons
        Button homeBtn = findViewById(R.id.homeButton);
        Button overviewBtn = findViewById(R.id.overviewButton);
        Button progressBtn = findViewById(R.id.progressButton);
        Button createBtn = findViewById(R.id.createButton);
        NavigationController navigationController = new NavigationController(this);

        homeBtn.setOnClickListener(navigationController);
        overviewBtn.setOnClickListener(navigationController);
        progressBtn.setOnClickListener(navigationController);
        createBtn.setOnClickListener(navigationController);

        // Initialize TaskController and views
        taskController = new TaskController(this);
        dailyProgressBar = findViewById(R.id.dailyProgressBar);
        monthlyProgressBar = findViewById(R.id.monthlyProgressBar);
        yearlyProgressBar = findViewById(R.id.yearlyProgressBar);

        dailyFlavorText = findViewById(R.id.dailyFlavorText);
        monthlyFlavorText = findViewById(R.id.monthlyFlavorText);
        yearlyFlavorText = findViewById(R.id.yearlyFlavorText);

        // Calculate and display progress with animations and flavor texts
        updateProgressBars();
    }

    /**
     * Updates the progress bars for daily, monthly, and yearly task completion
     * with animations and corresponding flavor texts.
     */
    private void updateProgressBars() {
        LocalDate currentDate = LocalDate.now();
        String date = currentDate.toString(); // Format: "yyyy-MM-dd"
        String month = date.substring(0, 7);  // Format: "yyyy-MM"
        String year = date.substring(0, 4);   // Format: "yyyy"

        // Update daily progress
        List<Task> dailyTasks = taskController.getTasksByDate(date);
        int completedDailyTasks = taskController.calculateCompletedTasks(date);
        animateProgressBar(dailyProgressBar, completedDailyTasks, dailyTasks.size());
        dailyFlavorText.setText(getMotivationalMessage((completedDailyTasks * 100) / Math.max(dailyTasks.size(), 1)));

        // Update monthly progress
        List<Task> monthlyTasks = taskController.getTasksByMonth(month);
        int completedMonthlyTasks = (int) monthlyTasks.stream().filter(Task::getCompleted).count();
        animateProgressBar(monthlyProgressBar, completedMonthlyTasks, monthlyTasks.size());
        monthlyFlavorText.setText(getMotivationalMessage((completedMonthlyTasks * 100) / Math.max(monthlyTasks.size(), 1)));

        // Update yearly progress
        List<Task> yearlyTasks = taskController.getTasksByYear(year);
        int completedYearlyTasks = (int) yearlyTasks.stream().filter(Task::getCompleted).count();
        animateProgressBar(yearlyProgressBar, completedYearlyTasks, yearlyTasks.size());
        yearlyFlavorText.setText(getMotivationalMessage((completedYearlyTasks * 100) / Math.max(yearlyTasks.size(), 1)));
    }

    /**
     * Animates the progress bar from 0 to the calculated progress value.
     *
     * @param progressBar The ProgressBar to animate.
     * @param completed   The number of completed tasks.
     * @param total       The total number of tasks.
     */
    private void animateProgressBar(ProgressBar progressBar, int completed, int total) {
        int progress = total > 0 ? (completed * 100) / total : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", 0, progress);
        animator.setDuration(1000); // Animation duration: 1 second
        animator.start();
    }

    /**
     * Generates a motivational message based on the percentage of tasks completed.
     *
     * @param progress The percentage of tasks completed.
     * @return A motivational message as a String.
     */
    private String getMotivationalMessage(int progress) {
        if (progress == 0) {
            return "Every journey begins with a single step!";
        } else if (progress < 50) {
            return "Keep pushing, you're making progress!";
        } else if (progress < 100) {
            return "You're almost there!";
        } else {
            return "Great job! You've achieved your goal!";
        }
    }

    /**
     * Refreshes the progress bars and flavor texts whenever the user navigates back to this activity.
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateProgressBars();
    }
}
