package com.demo.pomodoro.activity.settings;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.demo.pomodoro.R;
import com.demo.pomodoro.activity.todo.HomeActivity;
import com.demo.pomodoro.utils.Constant;
import com.demo.pomodoro.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.textView14)
    TextView textView14;
    @BindView(R.id.textView15)
    TextView textView15;
    @BindView(R.id.textView16)
    TextView textView16;
    @BindView(R.id.textView17)
    TextView textView17;
    @BindView(R.id.textView19)
    TextView textView19;
    @BindView(R.id.textView20)
    TextView textView20;
    @BindView(R.id.textView21)
    TextView textView21;
    @BindView(R.id.spn_duration)
    Spinner spnDuration;
    @BindView(R.id.spn_short)
    Spinner spnShort;
    @BindView(R.id.spn_long)
    Spinner spnLong;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.constraintLayout3)
    ConstraintLayout constraintLayout3;
    @BindView(R.id.img_timer)
    ImageView imgTimer;
    @BindView(R.id.textView18)
    TextView textView18;
    @BindView(R.id.img_notification)
    ImageView imgNotification;
    @BindView(R.id.sw_sound)
    Switch swSound;
    @BindView(R.id.sw_vib)
    Switch swVib;
    @BindView(R.id.img_back_focus)
    ImageView imgBackFocus;
    @BindView(R.id.layout_ads)
    RelativeLayout layoutAds;
    private String selectedDuration, selectedShort, selectedLong;
    private int pos_dura, pos_short, pos_long;
    private boolean check;
    private boolean checkVib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        final SharedPreferenceUtils utils = new SharedPreferenceUtils(this);

        selectedDuration = utils.getStringValue(Constant.KEY_POMO_DURATION, Constant.TWENTY_FIVE_MIN);
        selectedShort = utils.getStringValue(Constant.KEY_POMO_SHORT, Constant.FIVE_MIN);
        selectedLong = utils.getStringValue(Constant.KEY_POMO_LONG, Constant.TWENTY_MIN);


        final Typeface font_avo = Typeface.createFromAsset(getAssets(), "fonts/utm_avo.TTF");
        final Typeface font_bold = Typeface.createFromAsset(getAssets(), "fonts/utm_avobold.TTF");

        textView14.setTypeface(font_bold);
        textView15.setTypeface(font_bold);
        textView16.setTypeface(font_avo);
        textView17.setTypeface(font_avo);
        textView18.setTypeface(font_avo);
        textView19.setTypeface(font_bold);
        textView20.setTypeface(font_avo);
        textView21.setTypeface(font_avo);

        String[] arrayMinDura = {getString(R.string._15m),getString(R.string._20m), getString(R.string._25m), getString(R.string._30m),getString(R.string._35m),getString(R.string._40m),getString(R.string._45m)};
        ArrayAdapter<String> adapterDura = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayMinDura
        );
        adapterDura.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDuration.setAdapter(adapterDura);
        spnDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                utils.setValue(Constant.POSITION_DURATION, position);
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.spiner_color));
                ((TextView) parent.getChildAt(0)).setTypeface(font_bold);
                String object = spnDuration.getSelectedItem().toString();
                utils.setValue(Constant.KEY_POMO_DURATION, object);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pos_dura = utils.getIntValue(Constant.POSITION_DURATION, 2);
        spnDuration.setSelection(pos_dura);

        String[] arrayMinShort = {getString(R.string._2m), getString(R.string._5m),getString(R.string._10m)};
        ArrayAdapter<String> adapterShort = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayMinShort
        );
        adapterShort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnShort.setAdapter(adapterShort);
        spnShort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                utils.setValue(Constant.POS_SHORT, position);
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.spiner_color));
                ((TextView) parent.getChildAt(0)).setTypeface(font_bold);
                String object = spnShort.getSelectedItem().toString();
                utils.setValue(Constant.KEY_POMO_SHORT, object);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pos_short = utils.getIntValue(Constant.POS_SHORT, 1);
        spnShort.setSelection(pos_short);

        String[] arrayMinLong = {getString(R.string._10m), getString(R.string._15m), getString(R.string._20m), getString(R.string._30m), getString(R.string._35m)};
        ArrayAdapter<String> adapterLong = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayMinLong
        );
        adapterLong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLong.setAdapter(adapterLong);
        spnLong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                utils.setValue(Constant.POS_LONG, position);
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.spiner_color));
                ((TextView) parent.getChildAt(0)).setTypeface(font_bold);
                String object = spnLong.getSelectedItem().toString();
                utils.setValue(Constant.KEY_POMO_LONG, object);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pos_long = utils.getIntValue(Constant.POS_LONG, 3);
        spnLong.setSelection(pos_long);
        check = true;
        check = utils.getBoolanValue(Constant.SWITCH_SOUND, true);

        checkVib = true;
        checkVib = utils.getBoolanValue(Constant.SWITCH_VIBRATE, true);

        boolean on_off_sound = utils.getBoolanValue(Constant.ON_OFF_SOUND, true);
        swSound.setChecked(on_off_sound);
        boolean on_off_vibrate = utils.getBoolanValue(Constant.ON_OFF_VIBRATE, true);
        swVib.setChecked(on_off_vibrate);
        swSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check = true;
                    swSound.setChecked(check);
                    utils.setValue(Constant.SWITCH_SOUND, check);
                } else {
                    check = false;
                    swSound.setChecked(false);
                    utils.setValue(Constant.SWITCH_SOUND, check);
                }
                utils.setValue(Constant.ON_OFF_SOUND, isChecked);
            }

        });

        swVib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkVib = true;
                    swVib.setChecked(checkVib);
                    utils.setValue(Constant.SWITCH_VIBRATE, checkVib);
                } else {
                    checkVib = false;
                    swVib.setChecked(checkVib);
                    utils.setValue(Constant.SWITCH_VIBRATE, checkVib);
                }
                utils.setValue(Constant.ON_OFF_VIBRATE, isChecked);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBack.setVisibility(View.INVISIBLE);
                imgBackFocus.setVisibility(View.VISIBLE);
                startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
            }
        });

    }
}
