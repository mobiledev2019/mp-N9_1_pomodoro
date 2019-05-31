package com.demo.pomodoro.slide;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.demo.pomodoro.R;
import com.demo.pomodoro.activity.todo.HomeActivity;
import com.demo.pomodoro.utils.Constant;
import com.demo.pomodoro.utils.SharedPreferenceUtils;

public class SlideAdapter extends PagerAdapter {
    private Context context;
    LayoutInflater inflater;

    public SlideAdapter(Context context) {
        this.context = context;
    }

    public int[] lst_images = {
            R.drawable.description,
            R.drawable.timer,
            R.drawable.work,
            R.drawable.breaktime
    };

    public String [] lst_desciption = {
            "Choose 10 task todo for today",
            "Get ride of all distractions",
            "Put a check mark on a piece of paper when you finish",
            "If you have fewer than four checkmarks,take a short break (3- 5min) Otherwise take a long break 20 to 30 min"
    };

    public String[] lst_title = {
            "Decide on the task to be done",
            "Start the pomodoro timer (25 min)",
            "Work on the Task end work when the timer rings",
            "Reward yourself and take a break"
    };

    public int[] lst_background = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four
    };

    public String[] lst_number = {
            "1",
            "2",
            "3",
            "4"
    };


    @Override
    public int getCount() {
        return lst_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(ConstraintLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_slide,container,false);
        ConstraintLayout constraintLayout = view.findViewById(R.id.slide_layout);
        ImageView imgSlide = view.findViewById(R.id.img_slide);
        TextView txtTitle = view.findViewById(R.id.txt_title);
        TextView txtDescription = view.findViewById(R.id.txt_description);
        TextView txtNumber = view.findViewById(R.id.txt_number);
        TextView txtNext = view.findViewById(R.id.txt_next);
        constraintLayout.setBackgroundColor(lst_background[position]);
        imgSlide.setImageResource(lst_images[position]);
        txtTitle.setText(lst_title[position]);
        txtDescription.setText(lst_desciption[position]);
        txtNumber.setText(lst_number[position]);
        txtNext.setText("Finish");
        txtNext.setVisibility(View.GONE);
        if (txtNumber.getText().toString().equals("4")){
            txtNext.setVisibility(View.VISIBLE);
            txtNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, HomeActivity.class));
                    SharedPreferenceUtils utils = new SharedPreferenceUtils(context);
                    utils.setValue(Constant.PREFERENCES_FIRST,true);
                }
            });
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
