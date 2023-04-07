package com.example.week6_3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);

        imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.image2);
        viewFlipper.addView(imageView);

    }

    public void nextBtnClick(View v){
        viewFlipper.showNext();
    }

    public void prevBtnClick(View v){
        viewFlipper.showNext();
    }
}