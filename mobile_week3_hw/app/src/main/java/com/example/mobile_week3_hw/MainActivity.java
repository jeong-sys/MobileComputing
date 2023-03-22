package com.example.mobile_week3_hw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    double stored;
    char curOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("계산기");

        textView = findViewById(R.id.text);
    }
    public void onClick(View view){
        String current = textView.getText().toString();

        switch(view.getId()){
            case R.id.num_0:
                textView.setText(current + "0");
                break;
            case R.id.num_1:
                textView.setText(current + "1");
                break;
            case R.id.num_2:
                textView.setText(current + "2");
                break;
            case R.id.num_3:
                textView.setText(current + "3");
                break;
            case R.id.num_4:
                textView.setText(current + "4");
                break;
            case R.id.num_5:
                textView.setText(current + "5");

                break;
            case R.id.num_6:
                textView.setText(current + "6");

                break;
            case R.id.num_7:
                textView.setText(current + "7");

                break;
            case R.id.num_8:
                textView.setText(current + "8");

                break;
            case R.id.num_9:
                textView.setText(current + "9");

                break;

            case R.id.plus:
                if(current.equals("")){
                    Toast.makeText(getApplicationContext(), "숫자를 먼저 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    stored = Double.parseDouble(current);
                    curOperator = '+';
                    textView.setText("");
                    break;
                }

            case R.id.result:

                if(current.equals("")){
                    Toast.makeText(getApplicationContext(), "숫자를 먼저 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    double result_val = 0;
                    double thisValue = Double.parseDouble(textView.getText().toString());

                    switch(curOperator){
                        case '+':
                            result_val = stored + thisValue;
                            break;
                    }
                    // textView.setText("" + result_val)
                    String a = .getText().toString();
                    textView.setText(textView.getText().toString()+a);


                    stored = 0.0;
                    break;
                }
        }
    }
}