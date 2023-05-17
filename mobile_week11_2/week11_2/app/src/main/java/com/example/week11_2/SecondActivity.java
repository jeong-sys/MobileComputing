package com.example.week11_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    EditText ed1;
    EditText ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        ed1 = (EditText) findViewById(R.id.edit1);
        ed2 = (EditText) findViewById(R.id.edit2);

    }

    public void backClick(View v){
        finish();
    }

    public void thirdClick(View v){

        //Intent intent = new Intent(this,SecondActivity.class);

        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);

        intent.putExtra("name", "hwang");
        intent.putExtra("num1", Integer.parseInt(ed1.getText().toString()));
        intent.putExtra("num2", Integer.parseInt(ed2.getText().toString()));


        Bundle bundle = new Bundle();
        bundle.putString("name","ThisIsBundleName");
        int num[]= { 1,2,3,4};
        bundle.putIntArray("values",num);

        intent.putExtra("Bundle", bundle);

        startActivity(intent);

//   startActivityForResult(intent,12);

        // resultLauncher.launch(intent);

    }
}