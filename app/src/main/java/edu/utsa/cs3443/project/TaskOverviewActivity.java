package edu.utsa.cs3443.project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import edu.utsa.cs3443.project.controller.NavigationController;
import edu.utsa.cs3443.project.controller.TaskController;
import edu.utsa.cs3443.project.model.Task;

public class TaskOverviewActivity extends AppCompatActivity {
    private TaskController taskController;
    private ListView taskListView;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);

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


//        List<Task> tasks = taskController.fetchTasks();
//        TaskAdapter adapter = new TaskAdapter(this, tasks);
//        taskListView.setAdapter(adapter);

        loadTasks();
    }

    /**
     * Loads tasks from the TaskController and displays them in the ListView.
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

    public void updateTaskCompletion(Task task) {
        taskController.updateTaskCompletion(this, task);
        loadTasks(); // Reload tasks to ensure updated list is displayed
    }

}
