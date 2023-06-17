package com.example.project.ui.slideshow;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.databinding.FragmentSlideshowBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class mapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentSlideshowBinding binding;
    private GoogleMap mGoogleMap;
    private MapView mapView;

    //marker
    private boolean isAddingMarker = false;
    private boolean isRemovingMarker = false;
    static List<Marker> markers = new ArrayList<>();

    private static final String KEY_MARKERS = "com.example.project.KEY_MARKERS";

    // 여행기록 쓰기
    Button btnMapWrite;

    Button btnMark;
    Button btnMarkDel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapView = root.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //marker 생성
        btnMark = root.findViewById(R.id.mark);
        btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAddingMarker = true;
                isRemovingMarker = false;
            }
        });

        // 지도 위의 마커를 제거하는 버튼입니다.
        btnMarkDel = root.findViewById(R.id.markCancel);
        btnMarkDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 마커 추가 기능을 비활성화하고, 마커 제거 기능을 활성화합니다.
                isAddingMarker = false;
                isRemovingMarker = true;
            }
        });

// 새로운 트립을 작성하는 Activity를 시작하는 버튼입니다.
        btnMapWrite = root.findViewById(R.id.mapWrite);
        btnMapWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TripWriteActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    // Google Map이 준비되었을 때 호출되는 콜백 함수입니다.
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        // 준비된 GoogleMap 객체를 멤버 변수에 저장합니다.
        mGoogleMap = googleMap;
        // 지도의 줌 컨트롤을 활성화합니다.
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        // 이전에 저장한 마커들을 불러옵니다.
        loadMarkers();

        // 사용자가 지도를 클릭했을 때의 리스너를 설정합니다.
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                // 현재 마커를 추가하는 상태라면, 클릭한 위치에 마커를 추가합니다.
                if(isAddingMarker){
                    addMarker(latLng);
                }
            }
        });

        // 사용자가 마커를 클릭했을 때의 리스너를 설정합니다.
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                // 현재 마커를 제거하는 상태라면, 클릭한 마커를 제거합니다.
                if(isRemovingMarker){
                    marker.remove();
                    markers.remove(marker);
                    saveMarkers();
                }
                return true;
            }
        });
    }

    // 현재 마커들의 위치를 SharedPreferences에 저장하는 메소드입니다.
    private void saveMarkers() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        List<LatLng> latLngs = new ArrayList<>();
        for (Marker marker : markers) {
            latLngs.add(marker.getPosition());
        }
        String json = gson.toJson(latLngs);
        editor.putString(KEY_MARKERS, json);
        editor.apply();
    }

    // SharedPreferences에서 마커들의 위치를 불러오는 메소드입니다.
    private void loadMarkers() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_MARKERS, null);
        Type type = new TypeToken<ArrayList<LatLng>>() {}.getType();
        ArrayList<LatLng> latLngs = gson.fromJson(json, type);

        if (latLngs != null) {
            for (LatLng latLng : latLngs) {
                addMarker(latLng);
            }
        }
    }

    public void addMarker(LatLng latLng){
        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(latLng));
        markers.add(marker);
        saveMarkers();
    }

    // Fragment나 Activity의 생명주기와 관련된 작업 처리나, 리소스 관리 위해 사용

    // fragment가 사용자와 상호작용 시작할 때 호출
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    //현재 fragment일시중지할 떄 호출
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    // fragment와 관련되 view 리소스를 모두 해제
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // fragment상태 저장
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    // 메모리 최적화(지도 관련 메모리 부족할 때)
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
