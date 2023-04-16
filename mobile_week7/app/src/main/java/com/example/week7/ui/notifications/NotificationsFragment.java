package com.example.week7.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.week7.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        Button btn = (Button) binding.button;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                notificationsViewModel.chageText();
        }
        });

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root; }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
    }
}