package com.example.week11_hw_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text1, text2, text3;
    TextView[] text;
    int[] rate = {0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text = new TextView[] {text1, text2, text3};
    }

    public void imageClick(View v){
        int value;
        if(v.getId() == R.id.image1) value = 0;
        else if(v.getId() == R.id.image2) value = 1;
        else value = 2;
        if(rate[value] < 5) rate[value]++;
        text[value].setText("" + rate[value]);
    }

    public void btnClick(View v){
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("ra", rate);
        startActivityForResult(intent, 98);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            rate = data.getIntArrayExtra("rra");
            for(int i = 0; i < 3; i++) text[i].setText("" + rate[i]);
        }
    }
}