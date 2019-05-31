package com.demo.pomodoro.activity.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.pomodoro.R;
import com.demo.pomodoro.database.PomodoroDb;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodoActivity extends AppCompatActivity {

    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_description)
    EditText edtDescription;
    @BindView(R.id.btn_add_task)
    TextView btnAddTask;
    @BindView(R.id.btn_add_task_focus)
    TextView btnAddTaskFocus;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_back_focus)
    ImageView imgBackFocus;
    @BindView(R.id.layout_ads)
    RelativeLayout layoutAds;
    private PomodoroDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        ButterKnife.bind(this);

        Typeface font_avo = Typeface.createFromAsset(getAssets(), "fonts/utm_avo.TTF");
        Typeface font_bold = Typeface.createFromAsset(getAssets(), "fonts/utm_avobold.TTF");


        edtTitle.setTypeface(font_bold);
        edtDescription.setTypeface(font_avo);
        btnAddTask.setTypeface(font_bold);


    }

    @OnClick({R.id.img_back, R.id.btn_add_task})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if (edtTitle.getText().toString().length() <= 0) {
                    imgBack.setVisibility(View.INVISIBLE);
                    imgBackFocus.setVisibility(View.VISIBLE);
                    startActivity(new Intent(TodoActivity.this, HomeActivity.class));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage(R.string.back_to_do);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.stay, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton(R.string.leave, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            imgBack.setVisibility(View.INVISIBLE);
                            imgBackFocus.setVisibility(View.VISIBLE);
                            startActivity(new Intent(TodoActivity.this, HomeActivity.class));
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

                break;
            case R.id.btn_add_task:
                addTodo();
                break;
        }
    }

    private void addTodo() {
        if (edtTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_title), Toast.LENGTH_SHORT).show();
        } else {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String date = df.format(Calendar.getInstance().getTime());
            DateFormat currentMonth = new SimpleDateFormat("MM");
            db = new PomodoroDb(this);
            db.addJob(edtTitle.getText().toString(),
                    edtDescription.getText().toString(),
                    0,
                    date,
                    Integer.parseInt(currentMonth.format(Calendar.getInstance().getTime())),
                    0);

            btnAddTask.setVisibility(View.INVISIBLE);
            btnAddTaskFocus.setVisibility(View.VISIBLE);
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (edtTitle.getText().toString().length() <= 0) {
                imgBack.setVisibility(View.INVISIBLE);
                imgBackFocus.setVisibility(View.VISIBLE);
                startActivity(new Intent(TodoActivity.this, HomeActivity.class));
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.app_name);
                builder.setMessage(R.string.back_to_do);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.stay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.leave, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imgBack.setVisibility(View.INVISIBLE);
                        imgBackFocus.setVisibility(View.VISIBLE);
                        startActivity(new Intent(TodoActivity.this, HomeActivity.class));
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
