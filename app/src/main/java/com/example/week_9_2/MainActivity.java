package com.example.week_9_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.FileNameMap;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker dp;
    EditText edtDiary;
    Button btnWrite;
    String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);

        dp = (DatePicker) findViewById(R.id.datePicker);
        edtDiary = (EditText) findViewById(R.id.editText);
        btnWrite = (Button) findViewById(R.id.button);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                fileName = Integer.toString(year) + "_"
                        + Integer.toString(monthOfYear + 1) + "_"
                        + Integer.toString(dayOfMonth) + ".txt";
                String str = readDiary(fileName);

                edtDiary.setText(str);
//
//                if (mSwitch.isChecked())
//                    btnWrite.setEnabled(false);
//                else
//                    btnWrite.setEnabled(true);
//            }
        }});

        btnWrite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
//
//                    if (mSwitch.isChecked()) {
//                        Toast.makeText(getApplicationContext(),
//                                "ReadOnly mode", Toast.LENGTH_SHORT).show();
//                        return;
//                    }

                    FileOutputStream outFs = openFileOutput(fileName, MODE_PRIVATE);
                    // FileOutputStream outFs = new FileOutputStream(new File(getFilesDir(), fileName));
                    String str = edtDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(),
                            fileName + " 이 저장됨", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),
                            "error:" + e.toString(), Toast.LENGTH_SHORT).show();
                }}
            });}


    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            // inFs=new  FileInputStream(new File(getFilesDir(), fileName));
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            btnWrite.setText("수정 하기");
        } catch (IOException e) {
            edtDiary.setHint("일기 없음");
            btnWrite.setText("새로 저장");
        }
        return diaryStr;
    }}

