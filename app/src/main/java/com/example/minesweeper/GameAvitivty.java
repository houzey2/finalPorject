package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class GameAvitivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_avitivty);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Intent intent = getIntent();
        int choice = intent.getIntExtra("Choice", 0);
        if (choice == 0) {
            setContentView(new gameLogic(this, 5, width, height));
            //MediaPlayer mediaPlayer= MediaPlayer.create(this,R.raw.haoyunlai);
            //mediaPlayer.setLooping(true);
            //mediaPlayer.start();
        } else if (choice == 1) {
            setContentView(new gameLogic(this, 10, width, height));
            //MediaPlayer mediaPlayer= MediaPlayer.create(this,R.raw.haoyunlai);
            //mediaPlayer.setLooping(true);
            //mediaPlayer.start();
        } else if (choice == 2) {
            setContentView(new gameLogic(this, 15, width, height));
            //MediaPlayer mediaPlayer= MediaPlayer.create(this,R.raw.haoyunlai);
            //mediaPlayer.setLooping(true);
            //mediaPlayer.start();
        }
    }
}
