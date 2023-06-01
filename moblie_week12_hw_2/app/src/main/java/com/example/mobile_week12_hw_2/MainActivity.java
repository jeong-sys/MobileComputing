package com.example.mobile_week12_hw_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    Button btnBack, btnPlay, btnNext;
    SeekBar seekBar;
    ListView listView;
    TextView currentTime, totalTime;
    MediaPlayer mediaPlayer;

    boolean PAUSED;
    int[] mp3 = {R.raw.love, R.raw.dream, R.raw.summer};
    int p = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear);

        String[] titles = {"잔나비 - 주저하는 연인들을 위해", "잔나비 - 꿈과 책과 힘과 벽", "잔나비 - 뜨거운 여름밤은 가고 남은 건 볼품 없지만"};

        btnBack = (Button) findViewById(R.id.btnBack);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnNext = (Button) findViewById(R.id.btnNext);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        listView = (ListView) findViewById(R.id.listView);
        currentTime = (TextView) findViewById(R.id.currentTime);
        totalTime = (TextView) findViewById(R.id.totalTime);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, titles);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                p = i;
                if(mediaPlayer != null) mediaPlayer.reset();
                startSong();
            }
        });

        btnBack.setOnClickListener((view -> {
            p--;
            if(p<0) p=2;
            if(mediaPlayer != null) mediaPlayer.reset();
            startSong();
        }));

        btnPlay.setOnClickListener((view -> {
            if(!PAUSED){startSong();}
            else{stopSong();}
        }));

        btnNext.setOnClickListener((view -> {
            p++;
            if(p>2) p=0;
            if(mediaPlayer != null) mediaPlayer.reset();
            stopSong();
        }));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b && mediaPlayer != null) mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void startSong(){
        mediaPlayer = MediaPlayer.create(getApplicationContext(), mp3[p]);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        makeThread();
        PAUSED = true;
    }

    public void stopSong(){
        mediaPlayer.pause();
        PAUSED = false;
    }
    public void makeThread(){
        new Thread(){
            public void run(){
                final SimpleDateFormat dataformat = new SimpleDateFormat("mm:ss");
                if(mediaPlayer == null)
                    return;
                seekBar.setMax(mediaPlayer.getDuration());

                while(mediaPlayer.isPlaying()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(mediaPlayer.getCurrentPosition());
                            currentTime.setText(String.format(dataformat.format(mediaPlayer.getCurrentPosition())));
                            totalTime.setText(String.format(String.format(dataformat.format(mediaPlayer.getDuration()))));
                        }
                    });
                    SystemClock.sleep(100);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setProgress(0);
                        currentTime.setText(String.format(dataformat.format(0)));
                    }
                });
            }
        }.start();
    }
}