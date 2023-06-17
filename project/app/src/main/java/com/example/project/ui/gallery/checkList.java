package com.example.project.ui.gallery;

import android.R.layout;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.databinding.FragmentGalleryBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class checkList extends Fragment {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_ITEMS = "items";

    FragmentGalleryBinding binding;
    ListView listView;
    Button addBtn, deleteBtn;
    EditText edt;
    SharedPreferences sharedPreferences;

    // Fragment의 뷰를 생성하는 메소드입니다.
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // 레이아웃 인플레이터를 사용하여 FragmentGalleryBinding 클래스를 초기화하고, 그루트 뷰를 가져옵니다.
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 앱의 SharedPreferences를 가져옵니다.
        sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // SharedPreference에서 저장된 아이템들을 불러옵니다.
        final ArrayList<String> items = loadItemsFromPreferences();

        // ArrayAdapter를 생성하여 아이템들을 ListView에 표시할 수 있게 합니다.
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), layout.simple_list_item_single_choice, items);
        listView = root.findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        // 추가와 삭제 버튼, 그리고 입력 필드의 뷰를 찾아서 멤버 변수에 할당합니다.
        addBtn = root.findViewById(R.id.add);
        deleteBtn = root.findViewById(R.id.delete);
        edt = root.findViewById(R.id.editText);

        // 추가 버튼에 클릭 리스너를 설정합니다. 클릭 시 사용자가 입력한 텍스트를 리스트에 추가합니다.
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String newText = edt.getText().toString();
                if (!newText.isEmpty()){
                    items.add(newText);
                    adapter.notifyDataSetChanged();
                    edt.setText("");
                    saveItemsToPreferences(items);
                }
            }
        });

        // 삭제 버튼에 클릭 리스너를 설정합니다. 클릭 시 사용자가 선택한 아이템을 리스트에서 삭제합니다.
        deleteBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = adapter.getCount();
                int checked = listView.getCheckedItemPosition();
                if (checked >= 0 && checked < count) {
                    items.remove(checked);
                    adapter.notifyDataSetChanged();
                    saveItemsToPreferences(items);
                }
            }
        });

        return root;
    }

    // SharedPreferences에 ArrayList<String>을 저장하는 메소드입니다.
    private void saveItemsToPreferences(ArrayList<String> items) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString(KEY_ITEMS, json);
        editor.apply();
    }

    // SharedPreferences에서 ArrayList<String>을 불러오는 메소드입니다.
    private ArrayList<String> loadItemsFromPreferences() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_ITEMS, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> items = gson.fromJson(json, type);

        if (items == null) {
            items = new ArrayList<>();
        }

        return items;
    }

    // View 파괴 시 호출되는 메소드입니다.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // View가 파괴될 때 Binding 인스턴스를 null로 설정합니다.
        binding = null;
    }


}
