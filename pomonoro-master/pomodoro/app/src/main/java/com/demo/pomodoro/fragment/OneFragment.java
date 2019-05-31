package com.demo.pomodoro.fragment;


import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.demo.pomodoro.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {
    private static OneFragment INSTANCE = null;
    Unbinder unbinder;
    @BindView(R.id.textView22)
    TextView textView22;
    @BindView(R.id.textView23)
    TextView textView23;
    @BindView(R.id.textView24)
    TextView textView24;

    public static OneFragment getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new OneFragment();
        return INSTANCE;
    }

    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        unbinder = ButterKnife.bind(this, view);
        final Typeface font_avo = Typeface.createFromAsset(getContext().getAssets(), "fonts/utm_avo.TTF");
        final Typeface font_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/utm_avobold.TTF");
        textView22.setTypeface(font_bold);
        textView23.setTypeface(font_bold);
        textView24.setTypeface(font_avo);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
