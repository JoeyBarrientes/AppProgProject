package edu.utsa.cs3443.project;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * CalendarViewHolder represents a single day cell in the calendar view.
 * It binds the day text and handles click events.
 */
public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    /** TextView displaying the day of the month. */
    public final TextView dayOfMonth;

    /** Listener for handling item click events. */
    private final CalendarAdapter.OnItemListener onItemListener;

    /**
     * Constructs a new CalendarViewHolder with the specified view and item listener.
     *
     * @param itemView        The view representing a calendar cell.
     * @param onItemListener  The listener for item click events.
     */
    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener)
    {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    /**
     * Handles click events on the calendar cell.
     *
     * @param view The clicked view.
     */
    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }
}
