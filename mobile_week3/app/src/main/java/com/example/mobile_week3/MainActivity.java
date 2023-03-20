package com.example.mobile_week3;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //button눌렀을 때, 동작
    Button button1, button2, button4, button5;
    TextView textView;

    EditText ed1, ed2;
    String num1, num2;
    Integer result;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        화면 설정, R.layout.activity_main으로 화면 설정 (res/layout/activity_main/)
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);
        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);

// activity_main 에 있는 button1과 연결
        button1 = (Button) findViewById(R.id.button1);
// java new View.OnClickListner 구현 !
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "button1 click", Toast.LENGTH_SHORT).show();
                textView.setText("Button1 Click");

                num1 = ed1.getText().toString();
                num2 = ed2.getText().toString();

                result = Integer.parseInt(num1) + Integer.parseInt(num2);
                textView.setText("결과 :" + result.toString());
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "button2 click", Toast.LENGTH_SHORT).show();
            }
        });
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new MyOnClick());
    }
    public void btn3Click(View v){
        Toast.makeText(getApplicationContext(), "button3 click", Toast.LENGTH_SHORT).show();
    }
    public void onClick(View v){
        Toast.makeText(getApplicationContext(), "button4 click", Toast.LENGTH_SHORT).show();
    }
    class MyOnClick implements View.OnClickListener{
        public void onClick(View v){
            Toast.makeText(getApplicationContext(), "button5 click", Toast.LENGTH_SHORT).show();
        }
    }
}