package com.example.mobile_week12_hw;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editpeople, nameResult, peopleResult;
    Button btnAddData, btnReset, btnSelect, btnDelete, btnviewUpdate;
    SQLiteDatabase sqlDB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear);
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.name);
        editpeople = (EditText) findViewById(R.id.people);
        nameResult = (EditText) findViewById(R.id.nameResult);
        peopleResult = (EditText) findViewById(R.id.peopleResult);

        btnAddData = (Button) findViewById(R.id.btnAdd);
        btnviewUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnSelect = (Button) findViewById(R.id.btnSelect);

        btnReset.setOnClickListener((v)->{
            sqlDB = myDb.getWritableDatabase();
            myDb.onUpgrade(sqlDB, 1, 2);
            sqlDB.close();
        });

        btnAddData.setOnClickListener((v)->{
            sqlDB = myDb.getWritableDatabase();
            sqlDB.execSQL("INSERT INTO groupTBL VALUES('"+ editName.getText().toString() +"' , "+ editpeople.getText().toString() +");");
            sqlDB.close();
            Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myDb.getWritableDatabase();

                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);

                String strName = "\n\n\n"+"그룹이름"+ "\r\n" + "------" + "\r\n";
                String strNum = "\n\n\n"+"인원"+ "\r\n" + "------" + "\r\n";

                while (cursor.moveToNext()){
                    strName += cursor.getString(0)+"\r\n";
                    strNum += cursor.getString(1)+"\r\n";
                }

                nameResult.setText(strName);
                peopleResult.setText(strNum);

                cursor.close();
                sqlDB.close();
            }
        });

        btnviewUpdate.setOnClickListener((v)->{
            sqlDB = myDb.getWritableDatabase();
            sqlDB.execSQL("UPDATE groupTBL SET gNumber ='"+editpeople.getText().toString()+"' WHERE gName = '"+editName.getText().toString()+"';");
            sqlDB.close();
            Toast.makeText(getApplicationContext(), "수정됨", Toast.LENGTH_SHORT).show();
        });

        btnDelete.setOnClickListener((v)->{
            sqlDB = myDb.getWritableDatabase();
            sqlDB.execSQL("DELETE FROM groupTBL WHERE gName ='"+editName.getText().toString()+"';");
            sqlDB.close();
            Toast.makeText(getApplicationContext(), "삭제됨", Toast.LENGTH_SHORT).show();
        });
    }
}