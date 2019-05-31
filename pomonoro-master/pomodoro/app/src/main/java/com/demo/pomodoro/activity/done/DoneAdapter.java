package com.demo.pomodoro.activity.done;

import android.content.Context;
import android.graphics.Typeface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.pomodoro.R;

import java.util.List;

public class DoneAdapter extends RecyclerView.Adapter<DoneViewHolder> {
    private Context context;
    private List<Done> doneList;

    public DoneAdapter(Context context, List<Done> doneList) {
        this.context = context;
        this.doneList = doneList;
    }

    @NonNull
    @Override
    public DoneViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_done,viewGroup,false);
        return new DoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoneViewHolder doneViewHolder, int i) {
        Done done = doneList.get(i);
        Typeface font_avo = Typeface.createFromAsset(context.getAssets(), "fonts/utm_avo.TTF");
        Typeface font_bold = Typeface.createFromAsset(context.getAssets(), "fonts/utm_avobold.TTF");


        doneViewHolder.txtTitle.setTypeface(font_bold);
        doneViewHolder.txtDescription.setTypeface(font_avo);
        doneViewHolder.txtTitle.setText(done.getTitle());
        doneViewHolder.txtDescription.setText(done.getDescription());
    }

    @Override
    public int getItemCount() {
        return doneList.size();
    }
}
