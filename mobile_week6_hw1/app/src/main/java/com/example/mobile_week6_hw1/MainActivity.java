package com.example.mobile_week6_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.SeekBar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker datePicker;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_week6_1);

        datePicker = findViewById(R.id.datePicker);
        seekBar = findViewById(R.id.seekBar);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, datePicker.getYear());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        seekBar.setMax(days);

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                seekBar.setMax(days);
                seekBar.setProgress(day);
            }
        });

    }
}