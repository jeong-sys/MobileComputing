package com.example.project.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.DBHelper;
import com.example.project.R;
import com.example.project.TripItem;
import com.example.project.TripItemAdapter;
import com.example.project.TripRecord;

import java.util.List;

public class TripDetailActivity extends AppCompatActivity {
    private ListView listViewTripItems;
    private TextView textViewTitle, textViewStartDate, textViewEndDate, textViewTotalCost;
    private TripRecord tripRecord;
    private List<TripItem> tripItems;
    private TripItemAdapter tripItemAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 부모 클래스의 onCreate 메소드를 호출하여 기본 생성 작업을 수행합니다.
        super.onCreate(savedInstanceState);
        // activity_trip_detail.xml 레이아웃을 이용해 화면을 구성합니다.
        setContentView(R.layout.activity_trip_detail);

        // ActionBar를 가져와서 설정합니다.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 액션바의 제목을 "세부 내용"으로 설정합니다.
            actionBar.setTitle("세부 내용");
            // 액션바의 뒤로가기 버튼을 활성화합니다.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 레이아웃의 뷰들을 멤버 변수에 연결합니다.
        listViewTripItems = findViewById(R.id.listview_trip_items);
        textViewTitle = findViewById(R.id.textview_title);
        textViewStartDate = findViewById(R.id.textview_start_date);
        textViewEndDate = findViewById(R.id.textview_end_date);
        textViewTotalCost = findViewById(R.id.textview_total_cost);

        // 인텐트를 통해 전달된 TRIP_RECORD_ID를 가져옵니다.
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TRIP_RECORD_ID")) {
            int tripRecordId = intent.getIntExtra("TRIP_RECORD_ID", -1);
            // TRIP_RECORD_ID가 유효한 경우
            if (tripRecordId != -1) {
                dbHelper = DBHelper.getInstance(this);
                // 해당 ID에 대한 TripRecord를 데이터베이스에서 가져옵니다.
                tripRecord = dbHelper.getTripRecordById(tripRecordId);

                // 화면의 TextView들에 TripRecord의 정보를 설정합니다.
                textViewTitle.setText(tripRecord.getTitle());
                textViewStartDate.setText(tripRecord.getStartDate());
                textViewEndDate.setText(tripRecord.getEndDate());
                textViewTotalCost.setText(String.valueOf(tripRecord.getTotalCost()));

                // 이 여행 기록에 속하는 모든 여행 항목을 데이터베이스에서 가져옵니다.
                tripItems = dbHelper.getTripItemsByRecordId(tripRecord.getId());
                // ListView에 이를 표시하기 위한 어댑터를 생성하고 설정합니다.
                tripItemAdapter = new TripItemAdapter(this, tripItems);
                listViewTripItems.setAdapter(tripItemAdapter);

            } else {
                // TRIP_RECORD_ID가 유효하지 않은 경우 이 Activity를 종료합니다.
                finish();
            }
        } else {
            // TRIP_RECORD_ID가 전달되지 않은 경우 이 Activity를 종료합니다.
            finish();
        }
    }

    // 옵션 메뉴 아이템이 선택됐을 때의 처리를 담당하는 메소드입니다.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 뒤로 가기 버튼이 눌렸을 때
        if (item.getItemId() == android.R.id.home) {
            // 이 Activity를 종료하고 이전 화면으로 돌아갑니다.
            finish();
            return true;
        }
        // 그 외의 경우 부모 클래스의 메소드를 호출합니다.
        return super.onOptionsItemSelected(item);
    }
}