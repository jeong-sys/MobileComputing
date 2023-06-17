package com.example.project.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.project.DBHelper;
import com.example.project.R;
import com.example.project.TripRecord;

import java.util.List;

// 홈 화면의 Fragment 클래스입니다.
public class HomeFragment extends Fragment {

    // 데이터베이스 도우미 클래스를 저장하는 멤버 변수
    private DBHelper dbHelper;
    // 여행 기록을 표시하는 ListView
    private ListView lvTripRecords;
    // 여행 기록들을 저장하는 리스트, 클래스 전역 범위에서 접근할 수 있도록 설정
    private List<TripRecord> tripRecords;

    // Fragment의 뷰를 생성하는 메소드입니다.
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // fragment_home.xml 레이아웃을 사용하여 뷰를 생성합니다.
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // ListView와 DBHelper 객체를 초기화합니다. - 여행 목록
        lvTripRecords = root.findViewById(R.id.lv_trip_records);
        dbHelper = new DBHelper(getContext());

        // ListView 아이템 클릭 시 TripDetailActivity를 시작합니다. ( 저장된 정보를 보여준다 )
        lvTripRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),TripDetailActivity.class);
                intent.putExtra("TRIP_RECORD_ID", tripRecords.get(position).getId());
                startActivity(intent);
            }
        });

        // ListView 아이템을 길게 클릭했을 때 해당 아이템을 삭제합니다.
        lvTripRecords.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("삭제하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TripRecord tripRecordToDelete = tripRecords.get(position);
                        dbHelper.deleteTripRecord(tripRecordToDelete.getId());
                        loadTripRecords();
                    }
                });
                builder.setNegativeButton("취소", null);
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

        return root;
    }

    // Fragment가 화면에 다시 나타날 때 ListView를 갱신합니다.
    @Override
    public void onResume() {
        super.onResume();
        loadTripRecords();
    }

    // DBHelper를 사용하여 여행 기록을 불러와 ListView에 표시하는 메소드입니다.
    private void loadTripRecords() {
        tripRecords = dbHelper.getAllTripRecords();

        String[] tripRecordTitles = new String[tripRecords.size()];
        for (int i = 0; i < tripRecords.size(); i++) {
            tripRecordTitles[i] = tripRecords.get(i).getTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, tripRecordTitles);
        lvTripRecords.setAdapter(adapter);
    }
}