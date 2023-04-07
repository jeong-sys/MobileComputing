package com.example.week_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    Button btnStart, btnEnd;
//    CalendarView calendarView;
    TextView textView;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chronometer);

        btnStart = (Button) findViewById(R.id.Start);
        btnEnd = (Button) findViewById(R.id.END);
        chronometer = (Chronometer) findViewById(R.id.chronometer1);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

//        calendarView = (CalendarView) findViewById(R.id.calendarView);
        textView = (TextView) findViewById(R.id.textView);

//        CalendarView.OnDateChangeListener mDateChangeListener = new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
//
//                textView.setText(new String().format("year %d: month:%d, day:%d", i, i1+1, i2));
//            }
//        };
//
//        calendarView.setOnDateChangeListener(mDateChangeListener);

        DatePicker.OnDateChangedListener mDateChangeListener = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                textView.setText(new String().format("year %d: month:%d, day:%d", i, i1+1, i2));

            }
        };

        datePicker.setOnDateChangedListener(mDateChangeListener);

        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.setTextColor(Color.rgb(0,0,0));
            chronometer.start(); }
        });

        btnEnd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                chronometer.stop();
                chronometer.setTextColor(Color.rgb(255,0,0));
            }
        });
    }
}