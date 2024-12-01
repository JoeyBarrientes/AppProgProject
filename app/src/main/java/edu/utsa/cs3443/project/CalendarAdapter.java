package edu.utsa.cs3443.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/**
 * CalendarAdapter binds day data to the calendar grid and handles user interactions.
 */
class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    /** List of days to display in the calendar. */
    private final ArrayList<String> daysOfMonth;

    /** Listener for handling item click events. */
    private final OnItemListener onItemListener;

    /**
     * Constructs a CalendarAdapter with the specified days and item listener.
     *
     * @param daysOfMonth     The list of days to display.
     * @param onItemListener  The listener for item click events.
     */
    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener)
    {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    /**
     * Creates a new CalendarViewHolder for a calendar cell.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The view type of the new view.
     * @return A new CalendarViewHolder.
     */
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    /**
     * Binds data to a CalendarViewHolder at the specified position.
     *
     * @param holder   The CalendarViewHolder to bind.
     * @param position The position of the item in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
    }

    /**
     * Returns the total number of items in the calendar.
     *
     * @return The total number of days in the calendar.
     */
    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    /**
     * Interface for handling item click events in the calendar.
     */
    public interface  OnItemListener
    {
        /**
         * Called when a calendar day is clicked.
         *
         * @param position The position of the clicked item.
         * @param dayText  The text of the clicked day.
         */
        void onItemClick(int position, String dayText);
    }
}
