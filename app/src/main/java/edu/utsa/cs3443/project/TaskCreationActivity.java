package edu.utsa.cs3443.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import edu.utsa.cs3443.project.controller.NavigationController;
import edu.utsa.cs3443.project.controller.TaskController;

/**
 * TaskCreationActivity is an activity that allows users to create new tasks.
 * Users can input a task name, due date, and save the task to a CSV file.
 */
public class TaskCreationActivity extends AppCompatActivity {

    private EditText dueDate;
    private EditText descriptionEdt;
    private Button createBtn;

    /**
     * Initializes the activity, sets up navigation, and handles task creation logic.
     *
     * @param savedInstanceState A saved state from a previous instance, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_creation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.TaskCreationActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button homeBtn = findViewById(R.id.homeButton);
        Button overviewBtn = findViewById(R.id.overviewButton);
        Button progressBtn = findViewById(R.id.progressButton);
        Button createBtn = findViewById(R.id.createButton);
        NavigationController navigationController = new NavigationController(this);

        homeBtn.setOnClickListener(navigationController);
        overviewBtn.setOnClickListener(navigationController);
        progressBtn.setOnClickListener(navigationController);
        createBtn.setOnClickListener(navigationController);

        dueDate = findViewById(R.id.dueDateEditor);
        descriptionEdt = findViewById(R.id.descriptionEditor);
        createBtn = findViewById(R.id.createBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dueDate.getText().toString().trim();
                String description = descriptionEdt.getText().toString().trim();

                if (TextUtils.isEmpty(date) || TextUtils.isEmpty(description)){
                    Toast.makeText(v.getContext(), "All fields must be filled!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!TaskController.isValidDateFormat(date)){
                    Toast.makeText(v.getContext(), "Invalid Date Format (Must be yyyy-mm-dd)", Toast.LENGTH_LONG).show();
                    return;
                }

                TaskController.writeTaskToCSV(v.getContext(), date, description);
                dueDate.setText("");
                descriptionEdt.setText("");
            }
        });

    }
}