package edu.utsa.cs3443.project;

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

		TextView description = convertView.findViewById(R.id.taskDescription);
//		CheckBox completed = convertView.findViewById(R.id.);

		description.setText(task.getDescription());
//		completed.setChecked(task.getCompleted());

//		completed.setOnCheckedChangeListener((buttonView, isChecked) -> {
//			task.setCompleted(isChecked);
//			Toast.makeText(context, "Task marked as " + (isChecked ? "completed" : "incomplete"), Toast.LENGTH_SHORT).show();
//		});

		return convertView;
	}
}
