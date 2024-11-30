package edu.utsa.cs3443.project;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

import edu.utsa.cs3443.project.model.Task;

public class TaskAdapter extends ArrayAdapter<Task> {
	private Context context;

	public TaskAdapter(@NonNull Context context, @NonNull List<Task> tasks) {
		super(context, R.layout.task_item, tasks);
		this.context = context;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
		}

		Task task = getItem(position);
		TextView taskName = convertView.findViewById(R.id.taskName);
		TextView taskDueDate = convertView.findViewById(R.id.taskDueDate);

		taskName.setText(task.getDescription());
		taskDueDate.setText("Due: " + task.getDate());

		convertView.setOnClickListener(v -> showTaskDetails(task));
		return convertView;
	}

	private void showTaskDetails(Task task) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(task.getDescription())
				.setMessage("Due Date: " + task.getDate() +
						"\nDescription: " + task.getDescription() +
						"\nCompleted: " + (task.getCompleted() ? "Yes" : "No"))
				.setPositiveButton("OK", null)
				.show();
	}
}
