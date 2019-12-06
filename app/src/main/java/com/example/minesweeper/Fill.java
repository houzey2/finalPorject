package com.example.minesweeper;

public class Fill {
    private boolean mine = false;
    // number of mines around it. if the fill is a mine, num = -1.
    private int num;
    private boolean flag;
    private boolean clicked = false;
    public void setMine() {
        mine = true;
    }
    public void setnum(int setNum) {
        num = setNum;
    }
    public void setClicked(boolean state) {
        clicked = state;
    }
    public boolean getMine() {
        return mine;
    }
    public int getNum() {
        return num;
    }
    public boolean getClicked () {
        return clicked;
    }
    public void setFlag(){
        flag = true;
    }
    public boolean getFlag() {
        return flag;
    }
}
