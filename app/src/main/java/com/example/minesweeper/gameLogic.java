package com.example.minesweeper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class gameLogic extends View {
    private Map gameMaps;
    private Fill[][] map;
    private Context context;
    private int fillSize;
    private int length;
    private int x;
    private int y;
    private MediaPlayer mP;
    private long startTime;
    private long endTime;
    private int xCon;
    private int yCon;

    gameLogic(Context setContext, int mineNum, int width, int height) {
        super(setContext);
        this.context = setContext;
        gameMaps = new Map(mineNum, width, height);
        map = gameMaps.getMap();
        fillSize = gameMaps.getFillSize();
        length = gameMaps.getLength();
        x = gameMaps.getX();
        y = gameMaps.getY();
        mP= MediaPlayer.create(context,R.raw.hyl);
        mP.setLooping(true);
        mP.start();
    }

    public void judgement() {
        if (gameMaps.getFlagNum() == gameMaps.getMineNum()) {
            gameMaps.setGameState(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setMessage("Congratulations, you win!");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        gameMaps.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN) {
            xCon = (int) event.getX();
            yCon = (int) event.getY();
            startTime = event.getEventTime();
            endTime = startTime;
        } else if (event.getAction()== MotionEvent.ACTION_UP) {
            endTime = event.getEventTime();
            if (endTime - startTime > 500) {
                if ((xCon >= x && xCon <= length * fillSize + x) && (yCon >= y && yCon <= length * fillSize + y)) {
                    int xPosition = (int) (xCon - x) / fillSize;
                    int yPosition = (int) (yCon - y) / fillSize;
                    Fill current = map[yPosition][xPosition];
                    if (!current.getClicked()) {
                        if (map[yPosition][xPosition].getMine()) {
                            map[yPosition][xPosition].setClicked(true);
                            map[yPosition][xPosition].setFlag();
                            invalidate();
                            judgement();
                        } else {
                            gameMaps.wrongChoice();
                            invalidate();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setCancelable(true);
                            builder.setMessage("This grid is empty! HaHaHa!");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                    if (gameMaps.getWrongTimes() > 2) {
                        gameMaps.setGameState(true);
                        invalidate();
                        mP.stop();
                        MediaPlayer mediaPlayer= MediaPlayer.create(context,R.raw.aihe);
                        mediaPlayer.start();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setCancelable(false);
                        builder.setMessage("You made too many mistakes, Failed!");
                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
                System.out.println("longPress");
            } else if (endTime - startTime <= 500 && endTime - startTime >= 0){
                if ((xCon >= x && xCon <= length * fillSize + x) && (yCon >= y && yCon <= length * fillSize + y)) {
                    int xPosition = (int) ((xCon - x) / fillSize);
                    int yPosition = (int) ((yCon - y) / fillSize);
                    Fill current = map[yPosition][xPosition];
                    if (!current.getClicked()) {
                        map[yPosition][xPosition].setClicked(true);
                        if (current.getMine()) {
                            gameMaps.setGameState(true);
                            invalidate();
                            mP.stop();
                            MediaPlayer mediaPlayer= MediaPlayer.create(context,R.raw.aihe);
                            mediaPlayer.start();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setCancelable(false);
                            builder.setMessage("You Died!");
                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.exit(0);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            invalidate();
                            judgement();
                        }
                    }
                }
                System.out.println("Short");
            }
        }
        return true;
    }
}
