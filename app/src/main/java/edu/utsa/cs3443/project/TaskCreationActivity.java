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
import edu.utsa.cs3443.project.controller.TaskController;

public class TaskCreationActivity extends AppCompatActivity {

    private EditText dueDate;
    private EditText descriptionEdt;
    private Button createBtn;
    private Button backBtn;

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

        dueDate = findViewById(R.id.dueDateEditor);
        descriptionEdt = findViewById(R.id.descriptionEditor);
        createBtn = findViewById(R.id.createBtn);
        backBtn = findViewById(R.id.backBtn);

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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskCreationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}