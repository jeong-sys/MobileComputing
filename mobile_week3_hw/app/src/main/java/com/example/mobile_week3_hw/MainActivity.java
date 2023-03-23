package com.example.mobile_week3_hw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.DuplicateFormatFlagsException;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    double stored;
    String curOperator;

    String val;
    double val_plus;
    String val_2;
    String val_3;

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
                val = current + "0";
                break;

            case R.id.num_1:
                textView.setText(current + "1");
                val = current + "1";
                break;

            case R.id.num_2:
                textView.setText(current + "2");
                val = current + "2";
                break;

            case R.id.num_3:
                textView.setText(current + "3");
                val = current + "3";
                break;

            case R.id.num_4:
                textView.setText(current + "4");
                val = current + "4";
                break;

            case R.id.num_5:
                textView.setText(current + "5");
                val = current + "5";
                break;

            case R.id.num_6:
                textView.setText(current + "6");
                val = current + "6";
                break;

            case R.id.num_7:
                textView.setText(current + "7");
                val = current + "7";
                break;

            case R.id.num_8:
                textView.setText(current + "8");
                val = current + "8";
                break;

            case R.id.num_9:
                textView.setText(current + "9");
                val = current + "9";
                break;

            case R.id.plus:
                if(current.equals("")){
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
                else{
                    stored = Double.parseDouble(current);
                    curOperator = "+";
                    textView.setText("");
                    val_2 = val + " + ";
                    break;
                }

            case R.id.minus:
                if(current.equals("")){
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
                else{
                    stored = Double.parseDouble(current);
                    curOperator = "-";
                    textView.setText("");
                    val_2 = val + " - ";
                    break;
                }

            case R.id.mul:
                if(current.equals("")){
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
                else{
                    stored = Double.parseDouble(current);
                    curOperator = "*";
                    textView.setText("");
                    val_2 = val + " * ";
                    break;
                }

            case R.id.div:
                if(current.equals("")){
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
                else{
                    stored = Double.parseDouble(current);
                    curOperator = "/";
                    textView.setText("");
                    val_2 = val + " / ";
                    break;
                }

            case R.id.clear:
                textView.setText("");
                stored = 0;
                break;

            case R.id.result:

                // 숫자를 입력하지 않고 연산자나 = 누르는 경우
                if(current.equals("")){
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }

                else{
                    double result_val = 0;
                    double thisValue = Double.parseDouble(textView.getText().toString());

                    switch(curOperator){

                        case "+":
                            result_val = stored + thisValue;
                            val_3 = val_2 + val + " = " + result_val;
                            break;

                        case "-":
                            result_val = stored - thisValue;
                            val_3 = val_2 + val + " = " + result_val;
                            break;

                        case "*":
                            result_val = stored * thisValue;
                            val_3 = val_2 + val + " = " + result_val;
                            break;

                        case "/":
                            result_val = stored / thisValue;
                            val_3 = val_2 + val + " = " + result_val;
                            break;
                    }
                    textView.setText(val_3);

                    stored = 0.0;
                    break;
                }
        }
    }
}