package edu.utsa.cs3443.project.model;

public class Task {
    private String date;
    private String description;
    private boolean isCompleted;

    public Task(String date, String description, boolean isCompleted){
        this.date = date;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getDate(){
        return date;
    }

    public String getDescription(){
        return description;
    }

    public boolean getCompleted(){
        return isCompleted;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }
}
