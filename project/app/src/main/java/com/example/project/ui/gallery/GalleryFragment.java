package com.example.project.ui.gallery;

import android.R.layout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project.R;
import com.example.project.databinding.FragmentGalleryBinding;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private ListView listView;
    private Button addBtn, modifyBtn, deleteBtn;
    private EditText edt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ArrayList<String> items = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), layout.simple_list_item_single_choice, items);
        listView = root.findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        addBtn = root.findViewById(R.id.add);
        deleteBtn = root.findViewById(R.id.delete);
        edt = root.findViewById(R.id.editText);

        addBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String newText = edt.getText().toString();
                if (!newText.isEmpty()){
                    items.add(newText);
                    adapter.notifyDataSetChanged();
                    edt.setText("");
                }
            }
        });

        deleteBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = adapter.getCount();
                int checked = listView.getCheckedItemPosition();
                if (checked >= 0 && checked < count) {
                    items.remove(checked);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
