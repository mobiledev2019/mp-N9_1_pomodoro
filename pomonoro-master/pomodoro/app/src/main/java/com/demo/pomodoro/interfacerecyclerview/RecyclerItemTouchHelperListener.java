package com.demo.pomodoro.interfacerecyclerview;



import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Admin on 3/21/2018.
 */

public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
