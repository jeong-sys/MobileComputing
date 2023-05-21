package com.example.mobile_week7_hw;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_week7_hw.databinding.ActivityMainBinding;

public class Second_activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private boolean isLoggedIn = false;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem slideshowMenuItem = menu.findItem(R.id.nav_slideshow);
        slideshowMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 로그아웃 : NavigationView의 Header View에서 이름, 이메일, 이미지 기본값 변경
                View headerView = navigationView.getHeaderView(0);
                TextView nameTextView = headerView.findViewById(R.id.n_name);
                TextView emailTextView = headerView.findViewById(R.id.n_mail);
                ImageView profileImageView = headerView.findViewById(R.id.n_photo);

                nameTextView.setText(R.string.nav_header_title);
                emailTextView.setText(R.string.nav_header_subtitle);
                profileImageView.setImageResource(R.mipmap.ic_launcher_round);
                slideshowMenuItem.setTitle("Slideshow");

                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.login){
            show_alertdialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void show_alertdialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사용자 입력");
        builder.setIcon(R.mipmap.ic_launcher);

        // alertdialog.xml설정
        View alertdialog = getLayoutInflater().inflate(R.layout.alertdialog, null);
        builder.setView(alertdialog);

        EditText editName = alertdialog.findViewById(R.id.editname);
        EditText editMail = alertdialog.findViewById(R.id.editmail);
        RadioGroup radioGroup = alertdialog.findViewById(R.id.radio_group);

        RadioButton dog = alertdialog.findViewById(R.id.dog);
        RadioButton cat = alertdialog.findViewById(R.id.cat);
        RadioButton horse = alertdialog.findViewById(R.id.horse);


        // Navigation View의 Header View : 이름과 email, 사진을 교체
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                isLoggedIn = true;

                String name = editName.getText().toString();
                String email = editMail.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String photo = "";

                if (selectedId == dog.getId()) {
                    photo = "dog";
                } else if (selectedId == cat.getId()) {
                    photo = "cat";
                } else if (selectedId == horse.getId()) {
                    photo = "horse";
                }

                NavigationView navigationView = findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView headerName = headerView.findViewById(R.id.n_name);
                TextView headerEmail = headerView.findViewById(R.id.n_mail);
                ImageView headerPhoto = headerView.findViewById(R.id.n_photo);

                headerName.setText(name);
                headerEmail.setText(email);

                if (photo.equals("dog")) {
                    headerPhoto.setImageResource(R.drawable.dog);
                } else if (photo.equals("cat")) {
                    headerPhoto.setImageResource(R.drawable.cat);
                } else if (photo.equals("horse")) {
                    headerPhoto.setImageResource(R.drawable.horse);
                }

                //slideshow 메뉴 로그아웃 변경
                Menu menu = navigationView.getMenu();
                MenuItem slideshowMenuItem = menu.findItem(R.id.nav_slideshow);

                if (isLoggedIn) {
                    slideshowMenuItem.setTitle("로그아웃");
                }

                // 선택한 것 Toast출력
                Toast.makeText(Second_activity.this,"Name: "+name+",Email: "+email+",Photo: "+photo,Toast.LENGTH_SHORT).show();
            }
        });

        // cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}