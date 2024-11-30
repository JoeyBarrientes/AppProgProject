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
        taskController = new TaskController();
        taskListView = findViewById(R.id.TaskOverviewActivity);

        // Load and display all tasks
        loadTasks();
    }

    private void loadTasks() {
        List<Task> tasks = taskController.fetchTasks();
        displayTasks(tasks);
    }

    private void displayTasks(List<Task> tasks) {
        TaskAdapter adapter = new TaskAdapter(this, tasks);
        taskListView.setAdapter(adapter);
    }
}
