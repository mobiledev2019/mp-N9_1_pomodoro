package com.demo.pomodoro.activity.todo;


import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.pomodoro.R;
import com.demo.pomodoro.interfacerecyclerview.ItemClickListener;


public class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txt_title,txt_description,txt_count;
    private ItemClickListener itemClickListener;
    public RelativeLayout view_background;
    public ConstraintLayout view_foreground;
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TodoViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_title = itemView.findViewById(R.id.txt_title);
        txt_description = itemView.findViewById(R.id.txt_description);
        txt_count = itemView.findViewById(R.id.txt_count);
        view_background = itemView.findViewById(R.id.view_background);
        view_foreground = itemView.findViewById(R.id.view_foreground);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
