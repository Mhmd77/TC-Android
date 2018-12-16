package com.myapps.tc_android.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.myapps.tc_android.R;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RentCarActivity extends AppCompatActivity implements OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String START_DATEPICKER_TAG = "datepicker";
    public static final String START_TIMEPICKER_TAG = "timepicker";
    public static final String END_DATEPICKER_TAG = "datepicker";
    public static final String END_TIMEPICKER_TAG = "timepicker";
    @BindView(R.id.date_time_textview)
    TextView dateTimeTextview;
    @BindView(R.id.start_date_picker)
    ImageView startDatePicker;
    @BindView(R.id.start_date_text)
    TextView startDateText;
    @BindView(R.id.start_time_picker)
    ImageView startTimePicker;
    @BindView(R.id.start_time_text)
    TextView startTimeText;
    @BindView(R.id.end_date_time_textview)
    TextView endDateTimeTextview;
    @BindView(R.id.end_date_picker)
    ImageView endDatePicker;
    @BindView(R.id.end_date_text)
    TextView endDateText;
    @BindView(R.id.end_time_picker)
    ImageView endTimePicker;
    @BindView(R.id.end_time_text)
    TextView endTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);
        ButterKnife.bind(this);

        final Calendar startCalendar = Calendar.getInstance();
        final Calendar endCalendar = Calendar.getInstance();

        final DatePickerDialog startDatePicker = DatePickerDialog.newInstance(this, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH), false);
        final TimePickerDialog startTimePicker = TimePickerDialog.newInstance(this, startCalendar.get(Calendar.HOUR_OF_DAY), startCalendar.get(Calendar.MINUTE), false, false);
        final DatePickerDialog endDatePicker = DatePickerDialog.newInstance(new OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                endDateText.setText(year + "/" + month + "/" + day);
            }
        }, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH), false);
        final TimePickerDialog endTimePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                endTimeText.setText(hourOfDay+":"+minute);
            }
        }, endCalendar.get(Calendar.HOUR_OF_DAY), endCalendar.get(Calendar.MINUTE), false, false);

        findViewById(R.id.start_date_picker).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startDatePicker.setYearRange(2018, 2020);
                startDatePicker.show(getSupportFragmentManager(), START_DATEPICKER_TAG);
                startDatePicker.getSelectedDay().toString();

            }
        });

        findViewById(R.id.start_time_picker).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimePicker.show(getSupportFragmentManager(), START_TIMEPICKER_TAG);
            }
        });
        findViewById(R.id.end_date_picker).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                endDatePicker.setYearRange(2018, 2020);
                endDatePicker.show(getSupportFragmentManager(), END_DATEPICKER_TAG);
            }
        });

        findViewById(R.id.end_time_picker).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimePicker.show(getSupportFragmentManager(), END_TIMEPICKER_TAG);
            }
        });

        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(START_DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }
            DatePickerDialog dpd2 = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(END_DATEPICKER_TAG);
            if (dpd2 != null) {
                dpd2.setOnDateSetListener(this);
            }

            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(START_TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
            TimePickerDialog tpd2 = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(END_TIMEPICKER_TAG);
            if (tpd2 != null) {
                tpd2.setOnTimeSetListener(this);
            }
        }
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        startDateText.setText(year + "/" + month + "/" + day);

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        startTimeText.setText(hourOfDay+":"+minute);
    }
}
