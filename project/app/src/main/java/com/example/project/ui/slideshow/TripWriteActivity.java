package com.example.project.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.project.DBHelper;
import com.example.project.R;
import com.example.project.TripItem;
import com.example.project.TripRecord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TripWriteActivity extends AppCompatActivity {

    Button btnAdd_list, btnDel_list;
    LinearLayout llItems;
    TextView tvSelectedStartDate, tvSelectedEndDate, tvLocation, tvTotalCost;
    Button btnStartDatePicker, btnEndDataPicker, btnSave, btnInit;
    ImageView img_location, imageView, currentSelectedImageView;
    EditText tvTitle, edtCost, edtNote;

    DBHelper dbHelper;
    private Uri currentPhotoUri;

    int PICK_IMAGE_REQUEST = 1;
    int totalCost = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_trip);
        ActionBar ac = getSupportActionBar();
        ac.setTitle("여행 기록 하기");

        dbHelper = new DBHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvTitle = (EditText) findViewById(R.id.title);
        btnAdd_list = (Button) findViewById(R.id.btn_add);
        btnDel_list = (Button) findViewById(R.id.btn_del);
        llItems = (LinearLayout) findViewById(R.id.ll_items);
        btnStartDatePicker = (Button) findViewById(R.id.btn_start_date_picker);
        btnEndDataPicker = (Button) findViewById(R.id.btn_end_date_picker);
        tvSelectedStartDate = (TextView) findViewById(R.id.tv_selected_start_date);
        tvSelectedEndDate = (TextView) findViewById(R.id.tv_selected_end_date);
        tvTotalCost = (TextView) findViewById(R.id.tv_ResultCost);
        btnSave = (Button) findViewById(R.id.submit_post);
        btnInit = (Button) findViewById(R.id.btnInit);

        btnAdd_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate new item layout
                View newItem = getLayoutInflater().inflate(R.layout.item_layout, null);

                img_location = newItem.findViewById(R.id.location);
                imageView = newItem.findViewById(R.id.imageView);
                tvLocation = newItem.findViewById(R.id.tvLocation);
                edtCost = newItem.findViewById(R.id.edtCost);
                edtNote = newItem.findViewById(R.id.edtNote);

                img_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openLocationDialog();
                    }
                });

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentSelectedImageView = (ImageView) view;
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                });

                llItems.addView(newItem);
            }
        });

        btnDel_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if there are any items to remove
                if (llItems.getChildCount() > 0) {
                    // Remove last added view
                    llItems.removeViewAt(llItems.getChildCount() - 1);
                }
            }
        });

        // 날짜 입력
        btnStartDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvSelectedStartDate);
            }
        });

        btnEndDataPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvSelectedEndDate);
            }
        });

        // '저장' 버튼에 대한 클릭 이벤트 핸들러입니다.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 여행 제목, 시작 날짜, 종료 날짜를 가져옵니다.
                String title = tvTitle.getText().toString();
                String startDate = tvSelectedStartDate.getText().toString();
                String endDate = tvSelectedEndDate.getText().toString();
                // 총 비용을 계산하고 화면에 표시합니다.
                totalCost = calculateTotalCost();
                tvTotalCost.setText(String.valueOf(totalCost));

                // 사용자가 입력한 정보로 새로운 TripRecord 객체를 생성합니다.
                TripRecord tripRecord = new TripRecord();
                tripRecord.setTitle(title);
                tripRecord.setStartDate(startDate);
                tripRecord.setEndDate(endDate);
                tripRecord.setTotalCost(totalCost);

                // 생성한 TripRecord 객체를 데이터베이스에 저장합니다. 저장에 성공하면 해당 레코드의 ID를 반환합니다.
                long recordId = dbHelper.insertTripRecord(tripRecord);

                if (recordId != -1) {
                    // 데이터베이스에 저장 성공. 이제 여행 항목들을 저장합니다.
                    for (int i = 0; i < llItems.getChildCount(); i++) {
                        View itemView = llItems.getChildAt(i);

                        ImageView itemImage = itemView.findViewById(R.id.imageView);
                        TextView itemLocation = itemView.findViewById(R.id.tvLocation);
                        EditText itemCost = itemView.findViewById(R.id.edtCost);
                        EditText itemNote = itemView.findViewById(R.id.edtNote);

                        // 이미지를 Bitmap으로 변환하고, 이를 다시 Uri로 변환합니다.
                        Drawable drawable = itemImage.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        Uri imageUri = saveBitmapToFile(bitmap);

                        // 항목의 위치, 비용, 메모를 가져옵니다.
                        String location = itemLocation.getText().toString();
                        int cost = Integer.parseInt(itemCost.getText().toString());
                        String note = itemNote.getText().toString();

                        // 사용자가 입력한 정보로 새로운 TripItem 객체를 생성하고 데이터베이스에 저장합니다.
                        TripItem tripItem = new TripItem();
                        tripItem.setTripRecordId((int) recordId);
                        tripItem.setLocation(location);
                        tripItem.setImageUri(imageUri.toString());
                        tripItem.setCost(cost);
                        tripItem.setNote(note);

                        dbHelper.insertTripItem(tripItem);
                    }

                    // 사용자에게 저장 성공을 알립니다.
                    Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// '초기화' 버튼에 대한 클릭 이벤트 핸들러입니다.
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 입력한 모든 정보를 초기화합니다.
                llItems.removeAllViews();
                tvTitle.setText("");
                tvSelectedStartDate.setText("");
                tvSelectedEndDate.setText("");
                tvTotalCost.setText("");

                // 사용자에게 초기화 성공을 알립니다.
                String msg = "초기화되었습니다";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

    }

    // 뒤로 가기
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // datepicker
    private void showDatePickerDialog(final TextView textView) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TripWriteActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth);
                        textView.setText(selectedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    // 장소 가져오기
    private void openLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("위치 입력");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String location = input.getText().toString();
                tvLocation.setText(location);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    // 사진 불러오기, 장소 불러오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                if (currentSelectedImageView != null) {
                    currentSelectedImageView.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // 경비 계산
    private int calculateTotalCost() {
        int totalCost = 0;

        for (int i = 0; i < llItems.getChildCount(); i++) {
            View itemView = llItems.getChildAt(i);
            EditText edtCost = itemView.findViewById(R.id.edtCost);
            String costString = edtCost.getText().toString();

            // costString이 비어있지 않을 경우만 Integer로 변환하고 합산
            if (!costString.isEmpty()) {
                totalCost += Integer.parseInt(costString);
            }
        }

        return totalCost;
    }

    // 데이터 베이스
    private Uri saveBitmapToFile(Bitmap bitmap) {
        try {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";

            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save the file: path for use with ACTION_VIEW intents
            currentPhotoUri = FileProvider.getUriForFile(this,
                    "com.example.project.fileprovider",
                    image);

            FileOutputStream out = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            return currentPhotoUri;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

