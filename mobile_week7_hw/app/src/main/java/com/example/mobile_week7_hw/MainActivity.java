package com.example.mobile_week7_hw;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

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
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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

        // set the custom layout
        View alertdialog = getLayoutInflater().inflate(R.layout.alertdialog, null);
        builder.setView(alertdialog);

        // get references to the views in the custom layout
        EditText editName = alertdialog.findViewById(R.id.editname);
        EditText editMail = alertdialog.findViewById(R.id.editmail);
        RadioGroup radioGroup = alertdialog.findViewById(R.id.radio_group);

        // set up the radio group
        RadioButton dog = alertdialog.findViewById(R.id.dog);
        RadioButton cat = alertdialog.findViewById(R.id.cat);
        RadioButton horse = alertdialog.findViewById(R.id.horse);

        // set the positive and negative buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editName.getText().toString();
                String email = editMail.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String photoType = "";

                if (selectedId == dog.getId()) {
                    photoType = "Photo 1";
                } else if (selectedId == cat.getId()) {
                    photoType = "Photo 2";
                } else if (selectedId == horse.getId()) {
                    photoType = "Photo 3";
                }

                Toast.makeText(MainActivity.this, "Name: " + name + ", Email: " + email + ", Photo Type: " + photoType, Toast.LENGTH_SHORT).show();
            }
        });
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