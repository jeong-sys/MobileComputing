package com.example.week3_hw2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button btn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("계산기_2");

        editText = (EditText) findViewById(R.id.input);
        btn = (Button) findViewById(R.id.print);
        textView = (TextView) findViewById(R.id.result);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String str = editText.getText().toString();
                Expression e = new Expression(str);
                str = String.valueOf(e.calculate());

                textView.setText("결과 : " + str);
            }
        });
    }
}