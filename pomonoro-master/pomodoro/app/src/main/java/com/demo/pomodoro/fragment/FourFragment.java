package com.demo.pomodoro.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.demo.pomodoro.R;
import com.demo.pomodoro.activity.todo.HomeActivity;
import com.demo.pomodoro.utils.Constant;
import com.demo.pomodoro.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FourFragment extends Fragment {
    private static FourFragment INSTANCE = null;
    @BindView(R.id.txt_finish)
    TextView txtFinish;
    Unbinder unbinder;
    @BindView(R.id.textView22)
    TextView textView22;
    @BindView(R.id.textView23)
    TextView textView23;
    @BindView(R.id.textView24)
    TextView textView24;
    @BindView(R.id.txt_finish_focus)
    TextView txtFinishFocus;

    public static FourFragment getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new FourFragment();
        return INSTANCE;
    }

    public FourFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        unbinder = ButterKnife.bind(this, view);
        final Typeface font_avo = Typeface.createFromAsset(getContext().getAssets(), "fonts/utm_avo.TTF");
        final Typeface font_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/utm_avobold.TTF");
        textView22.setTypeface(font_bold);
        textView23.setTypeface(font_bold);
        textView24.setTypeface(font_avo);

        return view;
    }


    @OnClick(R.id.txt_finish)
    public void onViewClicked() {
        txtFinish.setVisibility(View.INVISIBLE);
        txtFinishFocus.setVisibility(View.VISIBLE);
        getContext().startActivity(new Intent(getContext(), HomeActivity.class));
        SharedPreferenceUtils utils = new SharedPreferenceUtils(getContext());
        utils.setValue(Constant.PREFERENCES_FIRST, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
