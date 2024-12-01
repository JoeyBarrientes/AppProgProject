package edu.utsa.cs3443.project.model;

/**
 * Task represents an individual task with attributes such as due date,
 * description, and completion status.
 */
public class Task {
    private String date;
    private String description;
    private boolean isCompleted;

    /**
     * Constructs a new Task with the specified attributes.
     *
     * @param date        The task's due date.
     * @param description The task's description.
     * @param isCompleted The task's completion status.
     */
    public Task(String date, String description, boolean isCompleted){
        this.date = date;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    /**
     * Gets the task's due date.
     *
     * @return The task's due date.
     */
    public String getDate(){
        return date;
    }

    /**
     * Gets the task's description.
     *
     * @return The task's description.
     */
    public String getDescription(){
        return description;
    }

    /**
     * Checks if the task is completed.
     *
     * @return True if the task is completed, false otherwise.
     */
    public boolean getCompleted(){
        return isCompleted;
    }

    /**
     * Sets the task's due date.
     *
     * @param date The new due date.
     */
    public void setDate(String date){
        this.date = date;
    }

    /**
     * Sets the task's description.
     *
     * @param description The new description.
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Sets the task's completion status.
     *
     * @param isCompleted The new completion status.
     */
    public void setCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }
}
