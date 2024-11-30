package edu.utsa.cs3443.project.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import edu.utsa.cs3443.project.*;

public class NavigationController implements View.OnClickListener {
    private Context context;

    public NavigationController(Context context) {
        this.context = context;
    }

    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.homeButton) {
            navigateTo(MainActivity.class);
        } else if (id == R.id.overviewButton) {
            navigateTo(TaskOverviewActivity.class);
        } else if (id == R.id.progressButton) {
            navigateTo(TaskProgressActivity.class);
        } else if (id == R.id.createButton) {
            navigateTo(TaskCreationActivity.class);
        } else {
            Toast.makeText(context, "Unknown Button clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}
