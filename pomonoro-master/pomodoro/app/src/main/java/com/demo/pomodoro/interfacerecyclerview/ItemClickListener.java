package com.demo.pomodoro.interfacerecyclerview;

import android.view.View;

/**
 * Created by Admin on 12/26/2017.
 */

public interface ItemClickListener {
    void onClick(View view, int posittion, boolean isLongClick);
}
