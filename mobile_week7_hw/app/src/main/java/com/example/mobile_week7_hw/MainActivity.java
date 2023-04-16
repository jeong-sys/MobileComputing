package com.example.mobile_week7_hw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_week7_hw.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private TextView headerName, headerEmail;
    private ImageView headerImage;
    private String selectedImage = "개";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_slideshow) {
                    // do something
                } else if (id == R.id.nav_logout) {
                    logout();
                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        View headerView = navigationView.getHeaderView(0);
        headerName = headerView.findViewById(R.id.header_name);
        headerEmail = headerView.findViewById(R.id.header_email);
        headerImage = headerView.findViewById(R.id.header_image);

        // Set default header info
        headerName.setText("이름");
        headerEmail.setText("이메일");
        headerImage.setImageResource(R.drawable.dog);

    }

    private void setSupportActionBar(Toolbar toolbar) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_login) {
            showLoginDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLoginDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_login, null);

        final EditText nameEditText = dialogView.findViewById(R.id.edit_text_name);
        final EditText emailEditText = dialogView.findViewById(R.id.edit_text_email);
        final RadioGroup radioGroup = dialogView.findViewById(R.id.radio_group);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("로그인")
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEditText.getText().toString();
                        String email = emailEditText.getText().toString();

                        // Get selected radio button
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        RadioButton radioButton = dialogView.findViewById(selectedId);
                        selectedImage = radioButton.getText().toString();

                        // Update header info
                        headerName.setText(name);
                        headerEmail.setText(email);
                        if (selectedImage.equals("개")) {
                            headerImage.setImageResource(R.drawable.dog);
                        } else if (selectedImage.equals("고양이")) {
                            headerImage.setImageResource(R.drawable.cat);
                        } else if (selectedImage.equals("말")) {
                            headerImage.setImageResource(R.drawable.horse);
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

