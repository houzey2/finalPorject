package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.widget.Button;


import java.util.Random;


public class Map{
    private int mineNum;
    private Fill[][] map;
    private int length;
    private int fillSize;

    private Paint drawLine;
    private Paint drawNum;
    private Paint drawMine;
    private int x;
    private int y;
    public int mapSize;
    private boolean gameFinish = false;
    private int wrongTimes = 0;

    public Map(final int setMineNum, final int width, final int height) {
        this.length = 8;
        map = new Fill[length][length];
        mineNum = setMineNum;
        fillSize = width / 10;
        x = (width - fillSize * length) / 2;
        y = (height - fillSize * length) / 2;

        mapSize = fillSize * length;

        //paint lines
        drawLine = new Paint();
        drawLine.setAntiAlias(true);
        drawLine.setColor(Color.BLACK);
        drawLine.setStyle(Paint.Style.STROKE);
        //paint num of mines around the current point
        drawNum = new Paint();
        drawNum.setAntiAlias(true);
        drawNum.setTextSize(fillSize);
        drawNum.setColor(Color.BLUE);
        // paint mine
        drawMine = new Paint();
        drawMine.setAntiAlias(true);
        drawMine.setColor(Color.RED);







        // generate mines
        Random x = new Random();
        Random y = new Random();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {

                Fill input = new Fill();
                map[i][j] = input;
            }
        }
        for (int i = 0; i <= setMineNum - 1; i++) {
            int xCon = x.nextInt(length - 1);
            int yCon = y.nextInt( length - 1);
            if (!map[yCon][xCon].getMine()) {
                map[yCon][xCon].setMine();
            } else {
                i--;
            }
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                Fill current = map[i][j];
                if (map[i][j].getMine()) {
                    current.setnum(-1);
                    continue;
                }
                int num = 0;
                if (i != 0 && j != 0 && map[i - 1][j - 1].getMine()) {
                    num++;
                }
                if (i != 0 && map[i - 1][j].getMine()) {
                    num++;
                }
                if (i != 0 && j < length - 1 && map[i - 1][j + 1].getMine()) {
                    num++;
                }
                if (j != 0 && map[i][j - 1].getMine()) {
                    num++;
                }
                if (i < length - 1 && j != 0 && map[i + 1][j - 1].getMine()) {
                    num++;
                }
                if (i < length - 1 && map[i + 1][j].getMine()) {
                    num++;
                }
                if (i < length - 1 && j < length - 1 && map[i + 1][j + 1].getMine()) {
                    num++;
                }
                if (j < length - 1 && map[i][j + 1].getMine()) {
                    num++;
                }
                current.setnum(num);
            }
        }
    }

    // draw the map
    public void draw(Canvas canvas) {
        canvas.drawRect(x, y, x + mapSize, y + mapSize, drawLine);
        for (int i = 0; i < length; i++) {
            canvas.drawRect(x, y + i * fillSize, x + mapSize, y + i * fillSize, drawLine);
        }
        for (int i = 0; i < length; i++) {
            canvas.drawRect(x + i * fillSize, y, x + i * fillSize, y + mapSize, drawLine);
        }
        canvas.drawText(wrongTimes + "/3", x, y - 50, drawNum);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                Fill current = map[i][j];
                if (current.getClicked()) {
                    if (current.getFlag()) {
                        canvas.drawCircle(x + j * fillSize + fillSize / 2, y + i * fillSize + fillSize / 2, fillSize / 2, drawMine);
                    } else if (current.getNum() >= 0) {
                        canvas.drawText(current.getNum() + " ", x + j * fillSize, y + (i + 1) * fillSize, drawNum);
                    }
                }
                if (gameFinish && current.getMine()) {
                    canvas.drawCircle(x + j * fillSize + fillSize / 2, y + i * fillSize + fillSize / 2, fillSize / 2, drawMine);
                }
            }
        }
    }

    public boolean getGameFinish() {
        return gameFinish;
    }
    public void setGameState(boolean gameState) {
        gameFinish = gameState;
    }

    public Fill[][] getMap() {
        return map;
    }
    public int getMineNum() {
        return mineNum;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getFillSize() {
        return fillSize;
    }
    public int getLength() {
        return length;
    }
    public int getFlagNum() {
        int count = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j].getFlag()) {
                    count++;
                }
            }
        }
        return count;
    }
    public int getWrongTimes() {
        return wrongTimes;
    }
    public void wrongChoice() {
        wrongTimes++;
    }
}
