package com.demo.pomodoro.activity.done;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.pomodoro.R;

public class DoneViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTitle,txtDescription;
    public DoneViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTitle = itemView.findViewById(R.id.txt_title);
        txtDescription = itemView.findViewById(R.id.txt_description);
    }
}
