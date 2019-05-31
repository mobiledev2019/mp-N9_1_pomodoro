package com.demo.pomodoro.activity.todo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.pomodoro.R;
import com.demo.pomodoro.interfacerecyclerview.ItemClickListener;
import com.demo.pomodoro.utils.Constant;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private Context context;
    private List<Todo> todoList;

    public TodoAdapter(Context context, List<Todo> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todo,viewGroup,false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder todoViewHolder, int i) {
        Todo todo = todoList.get(i);
        Typeface font_avo = Typeface.createFromAsset(context.getAssets(), "fonts/utm_avo.TTF");
        Typeface font_bold = Typeface.createFromAsset(context.getAssets(), "fonts/utm_avobold.TTF");

        todoViewHolder.txt_title.setTypeface(font_bold);
        todoViewHolder.txt_description.setTypeface(font_avo);

        final String id = String.valueOf(todo.getId());
        todoViewHolder.txt_title.setText(todo.getTitle());
        todoViewHolder.txt_description.setText(todo.getDescription());
        String count = String.valueOf(todo.getCount());
        todoViewHolder.txt_count.setText(count);
        if (todo.getCount()==0){
            todoViewHolder.txt_count.setVisibility(View.GONE);
        }
        else {
            todoViewHolder.txt_count.setVisibility(View.VISIBLE);
        }
        todoViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int posittion, boolean isLongClick) {
                Intent intent = new Intent(context,CountdownActivity.class);
                intent.putExtra(Constant.ID_TODO_ADAPTER,id);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void removeItem(int position){
        todoList.remove(position);
        notifyItemRemoved(position);

    }

    public Todo getItem(int position){
        return todoList.get(position);
    }
}
