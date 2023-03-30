package com.example.mobile_week4_hw;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    CheckBox check_1, check_2;
    RadioGroup rGroup_1, rGroup_2, rGroup_3;
    ImageView img_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check_1 = (CheckBox) findViewById(R.id.checkbox1);
        check_2 = (CheckBox) findViewById(R.id.checkbox2);

        rGroup_1 = (RadioGroup) findViewById(R.id.rGroup_1);
        rGroup_2 = (RadioGroup) findViewById(R.id.rGroup_2);
        rGroup_3 = (RadioGroup) findViewById(R.id.rGroup_3);

        img_view = (ImageView) findViewById(R.id.imageView);

        check_1.setOnCheckedChangeListener(mCheckListener);
        check_2.setOnCheckedChangeListener(mCheckListener);
        rGroup_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                StringBuilder mStr = new StringBuilder();

                if(i == R.id.m_sol){
                    img_view.setImageResource(R.drawable.sol);
                }
                if(i == R.id.m_in){
                    img_view.setImageResource(R.drawable.in);
                }
                if(i == R.id.m_moon){
                    img_view.setImageResource(R.drawable.moon);
                }
                if(i == R.id.m_sa){
                    img_view.setImageResource(R.drawable.hwa);
                }
            }
        });

        rGroup_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                StringBuilder mStr = new StringBuilder();

                if(i == R.id.m2_doo){
                    img_view.setImageResource(R.drawable.doo);
                }
                if(i == R.id.m2_one){
                    img_view.setImageResource(R.drawable.one);
                }
                if(i == R.id.m2_sung){
                    img_view.setImageResource(R.drawable.sung);
                }
                if(i == R.id.m2_young){
                    img_view.setImageResource(R.drawable.youngk);
                }
            }
        });

        rGroup_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                StringBuilder mStr = new StringBuilder();

                if(i == R.id.fitCenter){
                    img_view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }

                if(i == R.id.Matrix){
                    img_view.setScaleType(ImageView.ScaleType.MATRIX);
                    Matrix matrix = img_view.getImageMatrix();
                    float scale = 2.0f;
                    matrix.setScale(scale, scale);
                    img_view.setImageMatrix(matrix);
                }

                if(i == R.id.fitXY){
                    img_view.setScaleType(ImageView.ScaleType.FIT_XY);
                }

                if(i == R.id.center){
                    img_view.setScaleType(ImageView.ScaleType.CENTER);
                }
            }
        });
    }

    CompoundButton.OnCheckedChangeListener mCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            StringBuilder mStr = new StringBuilder();

            if (compoundButton.getId() == R.id.checkbox1){
                check_2.setChecked(false);
                rGroup_2.setVisibility(android.view.View.INVISIBLE);
                rGroup_1.setVisibility(android.view.View.VISIBLE);
                img_view.setImageResource(R.drawable.woman);
            }

            if (compoundButton.getId() == R.id.checkbox2){
                check_1.setChecked(false);
                rGroup_1.setVisibility(android.view.View.INVISIBLE);
                rGroup_2.setVisibility(android.view.View.VISIBLE);
                img_view.setImageResource(R.drawable.daysix);
            }
        }
    };
}