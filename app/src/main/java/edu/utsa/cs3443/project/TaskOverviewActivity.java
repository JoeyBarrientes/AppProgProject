package edu.utsa.cs3443.project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import edu.utsa.cs3443.project.controller.NavigationController;
import edu.utsa.cs3443.project.controller.TaskController;
import edu.utsa.cs3443.project.model.Task;


/**
 * TaskOverviewActivity is an activity that displays a list of tasks.
 * Users can view, delete, or update the completion status of tasks.
 */
public class TaskOverviewActivity extends AppCompatActivity {
    private TaskController taskController;
    private ListView taskListView;
    private TaskAdapter adapter;

    /**
     * Initializes the activity, sets up navigation, and loads the list of tasks.
     *
     * @param savedInstanceState A saved state from a previous instance, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);


        // Initializes the on screen buttons
        Button homeBtn = findViewById(R.id.homeButton);
        Button overviewBtn = findViewById(R.id.overviewButton);
        Button progressBtn = findViewById(R.id.progressButton);
        Button createBtn = findViewById(R.id.createButton);
        NavigationController navigationController = new NavigationController(this);

        homeBtn.setOnClickListener(navigationController);
        overviewBtn.setOnClickListener(navigationController);
        progressBtn.setOnClickListener(navigationController);
        createBtn.setOnClickListener(navigationController);

        // Initialize the controller and views
        taskController = new TaskController(this);
        taskListView = findViewById(R.id.TaskOverviewActivity);

        loadTasks();
    }

    /**
     * Loads tasks from the TaskController and displays them in the list.
     */
    private void loadTasks() {
        List<Task> tasks = taskController.fetchTasks();
        displayTasks(tasks);
    }

    /**
     * Displays a list of tasks in the ListView using the TaskAdapter.
     *
     * @param tasks List of tasks to display.
     */
    private void displayTasks(List<Task> tasks) {
        adapter = new TaskAdapter(this, tasks);
        taskListView.setAdapter(adapter);
    }

    /**
     * Deletes a task and refreshes the ListView to reflect the changes.
     *
     * @param task The task to delete.
     */
    public void deleteTask(Task task) {
        taskController.deleteTask(this, task);
        loadTasks(); // Reload tasks after deletion
    }

    /**
     * Updates the completion status of a task and reloads the task list.
     *
     * @param task The task to update.
     */
    public void updateTaskCompletion(Task task) {
        taskController.updateTaskCompletion(this, task);
        loadTasks(); // Reload tasks to ensure updated list is displayed
    }

}
