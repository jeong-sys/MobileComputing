package com.example.mobile_week6_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
public class MainActivity extends AppCompatActivity {
    DatePicker datePicker;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_week6_1);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                seekBar.setMax(days);
                seekBar.setProgress(day);
            }
        });
    }
}