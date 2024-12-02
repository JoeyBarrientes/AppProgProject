package edu.utsa.cs3443.project;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.widget.Toast;
import edu.utsa.cs3443.project.controller.NavigationController;
import edu.utsa.cs3443.project.controller.TaskController;
import edu.utsa.cs3443.project.model.Task;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity is the app's main screen displaying a calendar view.
 * Users can navigate between months and view selected dates.
 */
public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private TaskController taskController;

    /**
     * Initializes the activity, sets up the calendar view, and configures navigation.
     *
     * @param savedInstanceState A saved state from a previous instance, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
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

        taskController = new TaskController(this);
    }

    /**
     * Initializes widgets for the activity.
     */
    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    /**
     * Sets up the calendar view for the selected month.
     */
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    /**
     * Generates a list of days for the selected month.
     *
     * @param date The selected date.
     * @return An array list of days in the month.
     */
    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    /**
     * Formats a LocalDate to "MMMM yyyy".
     *
     * @param date The date to format.
     * @return The formatted date string.
     */
    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    /**
     * Displays the previous month's calendar.
     *
     * @param view The clicked view triggering the action.
     */
    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    /**
     * Displays the next month's calendar.
     *
     * @param view The clicked view triggering the action.
     */
    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    /**
     * Handles clicks on individual calendar days.
     *
     * @param position The position of the clicked day in the grid.
     * @param dayText  The text of the clicked day.
     */
    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.isEmpty()) {
            LocalDate clickedDate = selectedDate.withDayOfMonth(Integer.parseInt(dayText));
            String fullDate = clickedDate.toString();

            List<Task> tasksForDay = taskController.getTasksByDate(fullDate);

            // Inflate custom layout for the AlertDialog
            View dialogView = getLayoutInflater().inflate(R.layout.alert_task_view, null);
            LinearLayout taskContainer = dialogView.findViewById(R.id.taskContainer);

            if (tasksForDay != null && !tasksForDay.isEmpty()) {
                for (Task task : tasksForDay) {
                    TextView taskTextView = new TextView(this);
                    taskTextView.setText("• " + task.getDescription());
                    taskTextView.setTextSize(16);

                    if (task.getCompleted()) {
                        // Apply strike-through effect for completed tasks
                        taskTextView.setPaintFlags(taskTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        taskTextView.setTextColor(Color.GRAY); // Set text color for completed tasks
                    } else {
                        // Set default text color for incomplete tasks
                        taskTextView.setTextColor(Color.BLACK);
                    }

                    taskContainer.addView(taskTextView); // Add TextView to the container
                }
            } else {
                TextView noTasksTextView = new TextView(this);
                noTasksTextView.setText("No tasks for this date.");
                noTasksTextView.setTextSize(16);
                noTasksTextView.setTextColor(Color.BLACK);
                taskContainer.addView(noTasksTextView);
            }

            // Build and show the AlertDialog
            new AlertDialog.Builder(this)
                    .setTitle("Tasks for " + fullDate)
                    .setView(dialogView) // Use custom layout
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }
}









