package com.example.week11_hw_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    RatingBar rating1, rating2, rating3;
    RatingBar[] ra;
    int[] in;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);

        rating1 = (RatingBar) findViewById(R.id.rate1);
        rating2 = (RatingBar) findViewById(R.id.rate2);
        rating3 = (RatingBar) findViewById(R.id.rate3);
        ra = new RatingBar[] {rating1, rating2, rating3};

        Intent intent = getIntent();
        in = intent.getIntArrayExtra("ra");
        if(intent != null) for(int i = 0; i < 3; i++) ra[i].setRating(in[i]);
    }

    public void returnClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        for(int i = 0; i < 3; i++) in[i] = (int)ra[i].getRating();
        intent.putExtra("rra", in);
        setResult(RESULT_OK, intent);
        finish();
    }
}
