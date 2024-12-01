package edu.utsa.cs3443.project.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import edu.utsa.cs3443.project.*;

/**
 * NavigationController handles navigation between activities.
 * It listens for button clicks and navigates to the appropriate activity.
 */
public class NavigationController implements View.OnClickListener {
    private Context context;

    /**
     * Constructs a new NavigationController with the given context.
     *
     * @param context The application context for navigation.
     */
    public NavigationController(Context context) {
        this.context = context;
    }

    /**
     * Navigates to the specified target activity.
     *
     * @param targetActivity The activity class to navigate to.
     */
    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    /**
     * Handles button click events and navigates based on button IDs.
     *
     * @param view The clicked view.
     */
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
