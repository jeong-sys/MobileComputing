package com.example.week_9;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    EditText ed;
    ImageView imageView;
    private final int MY_PERMISSION_REQUEST_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear);

        ed = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);
        if (Build.VERSION.SDK_INT >= 23) checkPermission();

    }

    public void clearText(View v) {
        ed.setText("");
    }

    public void sharedPreferencesSaveClick(View v) {

        SharedPreferences settings = getSharedPreferences("testShared", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", ed.getText().toString());
        editor.commit();
    }

    public void sharedPreferencesLoadClick(View v) {

        SharedPreferences settings = getSharedPreferences("testShared", MODE_PRIVATE);
        String str = settings.getString("name", "");
        ed.setText(str);
    }

    public void internalSaveClick(View v) {
        try {
            FileOutputStream outFs = openFileOutput("internal.txt", MODE_PRIVATE);
            String str = ed.getText().toString();
            outFs.write(str.getBytes());
            outFs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void internalLoadClick(View v) {
        try {
            FileInputStream inFs = openFileInput("internal.txt");
            //   FileInputStream inFs=new  FileInputStream(new File(getFilesDir(), "internal.txt"));
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            ed.setText(new String(txt));
        } catch (IOException e) {
        }
    }


    public void externalSaveClick(View v) {
        // String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //Log.d("hwang", "external storage path=" + strSDpath);
        //final File myDir = new File(strSDpath + "/mydir");

        File myDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/mydir");   //
        myDir.mkdir();

        Log.d("hwang", myDir.toString());

        try {
            FileOutputStream outFs = new FileOutputStream(new File(myDir, "external.txt"));
            String str = ed.getText().toString();
            outFs.write(str.getBytes());
            outFs.close();
            Log.d("hwang", "external save ok");
        } catch (IOException e) {
            Log.d("hwang", "external save error" + e.toString());
        }
    }

    public void externalLoadClick(View v) {
        try {
            //final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();   //  no app permission required. api>=23
            // final File myDir = new File(strSDpath + "/mydir");

            //directory 위치 설정 , 파일 저장장
           File myDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/mydir");
            FileInputStream inFs = new FileInputStream(new File(myDir, "external.txt"));
            byte[] txt = new byte[500];
            inFs.read(txt);  // read()
            inFs.close();
            ed.setText(new String(txt));
        } catch (IOException e) {
            Log.d("hwang", "external load error" + e.toString());
        }

    }

    public void copyClick(View v) {
        // internal to external //
/*
        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        try {
            FileInputStream inFs = openFileInput("internal.txt");
            FileOutputStream outFs = new FileOutputStream(new File(strSDpath + "/mydir", "internal.txt"));  //permission
            int c;
            while ((c = inFs.read()) != -1)
                outFs.write(c);

            outFs.close();
            inFs.close();
        } catch (IOException e) {
        }
        */
        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        final String strSDpath2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();

        Log.d("hwang", strSDpath + "   public path= " + strSDpath2);
        try {

            InputStream inFs = getResources().openRawResource(R.raw.hanbat);

            FileOutputStream outFs = new FileOutputStream(new File(strSDpath, "hanbatcopy.png"));
            FileOutputStream outFs2 = new FileOutputStream(new File(strSDpath2, "hanbatcopy.png"));
            int c;
            while ((c = inFs.read()) != -1) {
                outFs.write(c);
                outFs2.write(c);
            }

            outFs.close();
            outFs2.close();
            inFs.close();
        } catch (IOException e) {
            Log.d("hwang", "error" + e.toString());
        }

    }
    public void deleteClick(View v) {

        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(strSDpath, "hanbatcopy.png");

        file.delete();
    }

    public void showimageClick(View v) {

        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(strSDpath, "hanbatcopy.png");
        imageView.setImageURI(Uri.fromFile(file));
    }

    private void checkPermission() {
        //Log.d("hwang","check=" + checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_STORAGE);
            // MY_PERMISSION_REQUEST_STORAGE is an app-defined int constant

        } else {
            //Log.e(TAG, "permission deny");
            //writeFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult( requestCode,  permissions,  grantResults);

        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to write the permission.
                    Toast.makeText(this, "Read/Write external storage 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                }

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted,
                    Toast.makeText(this, "권한 승인", Toast.LENGTH_SHORT).show();
                    Log.d("hwang", "Permission granted");

                } else {
                    Log.d("hwang", "Permission deny");
                    // permission denied,
                }
                break;
        }
    }
}