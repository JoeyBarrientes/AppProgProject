package edu.utsa.cs3443.project;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import java.util.List;
import edu.utsa.cs3443.project.model.Task;

/**
 * TaskAdapter binds task data to a ListView.
 * It allows users to view, update, and delete tasks.
 */
public class TaskAdapter extends ArrayAdapter<Task> {
	private Context context;

	/**
	 * Constructs a TaskAdapter.
	 *
	 * @param context The application context.
	 * @param tasks   The list of tasks to bind.
	 */
	public TaskAdapter(@NonNull Context context, @NonNull List<Task> tasks) {
		super(context, R.layout.task_item, tasks);
		this.context = context;
	}

	/**
	 * Provides a view for each task in the list.
	 *
	 * @param position    The position of the item.
	 * @param convertView The recycled view to populate.
	 * @param parent      The parent view group.
	 * @return The populated view for the task.
	 */
	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
		}

		Task task = getItem(position);
		TextView taskName = convertView.findViewById(R.id.taskName);
		TextView taskDueDate = convertView.findViewById(R.id.taskDueDate);
		CheckBox completedCheckBox = convertView.findViewById(R.id.completedCheckBox);

		taskName.setText(task.getDescription());
		taskDueDate.setText("Due: " + task.getDate());
		completedCheckBox.setChecked(task.getCompleted());

		completedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
			task.setCompleted(isChecked);
			((TaskOverviewActivity) context).updateTaskCompletion(task); // Update CSV
		});

		convertView.setOnClickListener(v -> showTaskDetails(task));
		return convertView;
	}

	/**
	 * Displays a popup with task details and a delete option.
	 *
	 * @param task The task whose details are displayed.
	 */
	private void showTaskDetails(Task task) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(task.getDescription())
				.setMessage("Due Date: " + task.getDate() +
						"\nDescription: " + task.getDescription() +
						"\nCompleted: " + (task.getCompleted() ? "Yes" : "No"))
				.setPositiveButton("OK", null)
				.setNegativeButton("Delete", (dialog, which) -> {
					// Call deleteTask method
					((TaskOverviewActivity) context).deleteTask(task);
				})
				.show();
	}
}
