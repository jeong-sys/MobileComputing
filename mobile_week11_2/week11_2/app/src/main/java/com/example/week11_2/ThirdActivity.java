package com.example.week11_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    TextView txt_result;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);

        txt_result = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();

        if (intent != null) {

            result = intent.getIntExtra("num1", 0) + intent.getIntExtra("num2", 0);
            String name = intent.getStringExtra("name");

            txt_result.setText("Received Name:" + name + " Add value=" + result);
        }
    }

    public void backClick(View v) {
        finish();
    }

    public void thirdClick(View v) {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    public void returnMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
