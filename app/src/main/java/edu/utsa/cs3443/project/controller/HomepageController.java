package edu.utsa.cs3443.project.controller;

import edu.utsa.cs3443.project.*;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

/**
 * HomepageController is a Java controller class that handles
 * user interactions in the main activity view.
 * This controller determines the appropriate action based on the button clicked
 * and starts the corresponding activity.
 */
public class HomepageController implements View.OnClickListener {

    private Context context;

    /**
     * Constructs a new HomepageController with the specified context.
     *
     * @param context the context activity in which this controller operates.
     */
    public HomepageController(Context context) {
        this.context = context;
    }

    /**
     * Navigates to the Homepage view.
     */
    public void navigateToHomepage() {
        Intent homepageIntent = new Intent(context, MainActivity.class);
        context.startActivity(homepageIntent);
    }

    /**
     * Navigates to the Task Overview view.
     */
    public void navigateToOverview() {
        Intent overviewIntent = new Intent(context, TaskOverviewActivity.class);
        context.startActivity(overviewIntent);
    }

    /**
     * Navigates to the Task Progress view.
     */
    public void navigateToProgress() {
        Intent progressIntent = new Intent(context, TaskProgressActivity.class);
        context.startActivity(progressIntent);
    }

    /**
     * Navigates to the Task Creation view.
     */
    public void navigateToCreate() {
        Intent createIntent = new Intent(context, TaskCreationActivity.class);
        context.startActivity(createIntent);
    }

    /**
     * Handles button click events in the main view.
     * Determines the action to take based on the button clicked.
     *
     * @param view the clicked view, expected to be one of the main view buttons.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.homeButton) {
            navigateToHomepage();
        } else if (view.getId() == R.id.overviewButton) {
            navigateToOverview();
        } else if (view.getId() == R.id.progressButton) {
            navigateToProgress();
        } else if (view.getId() == R.id.createButton) {
            navigateToCreate();
        } else {
            Toast.makeText(context, "Unknown Button clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}


