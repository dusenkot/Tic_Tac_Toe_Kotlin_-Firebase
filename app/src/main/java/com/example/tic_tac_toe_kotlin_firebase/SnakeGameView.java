package com.example.tic_tac_toe_kotlin_firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.MotionEvent;
import android.widget.Switch;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nullable;

public class SnakeGameView extends View {
    private Bitmap bmGrass1, bmGrass2, bmSnake, bmApple;
    public static int sizeOfMap = 75*ConstantsSnake.SCREEN_WIDTH/1080;
    private int h = 21, w = 12;
    private ArrayList<SnakeGrass> arrGrass = new ArrayList<>();
    private Snake snake;
    private boolean move = false;
    private float mx, my;
    private Handler handler;
    private Runnable r;
    private Apple apple;
    public SnakeGameView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        bmGrass1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        bmGrass1 = Bitmap.createScaledBitmap(bmGrass1,sizeOfMap, sizeOfMap,true );
        bmGrass2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass03);
        bmGrass2 = Bitmap.createScaledBitmap(bmGrass2,sizeOfMap, sizeOfMap,true );
        bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake1);
        bmSnake = Bitmap.createScaledBitmap(bmSnake,14*sizeOfMap, sizeOfMap,true );
        bmApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        bmApple = Bitmap.createScaledBitmap(bmApple,sizeOfMap, sizeOfMap,true );

        int xOffset = (ConstantsSnake.SCREEN_WIDTH - w * sizeOfMap) / 2;
        int yOffset = (ConstantsSnake.SCREEN_HEIGHT - h * sizeOfMap) / 2;

        for(int i = 0; i < h; i++){
            for (int j = 0; j < w; j++) {
                Bitmap bm = (i + j) % 2 == 0 ? bmGrass1 : bmGrass2;
                int x = j * sizeOfMap + xOffset;
                int y = i * sizeOfMap + yOffset;
                arrGrass.add(new SnakeGrass(bm, x, y, sizeOfMap, sizeOfMap));
            }
        }
        snake = new Snake(bmSnake, arrGrass.get(126).getX(), arrGrass.get(126).getY(),4);
        apple = new Apple(bmApple, arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (!snake.isMove_bottom()) {
                    snake.setMove_top(true);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (!snake.isMove_top()) {
                    snake.setMove_bottom(true);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (!snake.isMove_right()) {
                    snake.setMove_left(true);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (!snake.isMove_left()) {
                    snake.setMove_right(true);
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();
        switch(a){
            case MotionEvent.ACTION_MOVE:{
                if (move == false){
                    mx = event.getX();
                    my = event.getY();
                    move = true;
                }else{
                    if (mx - event.getX()>100*ConstantsSnake.SCREEN_WIDTH/1080 && !snake.isMove_right()){
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_right(true);
                    }else if (event.getX() - mx>100*ConstantsSnake.SCREEN_WIDTH/1080 && !snake.isMove_left()){
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_left(true);
                    }else if (my - event.getY()>100*ConstantsSnake.SCREEN_WIDTH/1080 && !snake.isMove_bottom()){
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_top(true);
                    }else if (event.getY() - my>100*ConstantsSnake.SCREEN_WIDTH/1080 && !snake.isMove_top()){
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_bottom(true);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                mx = 0;
                my = 0;
                move = false;
                break;
            }
        }
        return true;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
//        backgropund color after 0xff
        canvas.drawColor(0xFFb5aa5e);
        for (int i = 0; i < arrGrass.size(); i++){
            canvas.drawBitmap(arrGrass.get(i).getBm(), arrGrass.get(i).getX(), arrGrass.get(i).getY(),null);
        }
        snake.update();
        snake.draw(canvas);
        apple.draw(canvas);
        if (snake.getArrPartSnake().get(0).getrBody().intersect(apple.getR())){
            randomApple();
            apple.reset(arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
        }
        handler.postDelayed(r, 200);
    }

    public int[] randomApple(){
        int[]xy = new int[2];
        Random r = new Random();
        xy[0] = r.nextInt(arrGrass.size()-1);
        xy[1] = r.nextInt(arrGrass.size()-1);
        Rect rect = new Rect(arrGrass.get(xy[0]).getX(), arrGrass.get(xy[1]).getY(),arrGrass.get(xy[0]).getX()+sizeOfMap, arrGrass.get(xy[1]).getY()+sizeOfMap);
        boolean check = true;
        while (check){
            check = false;
            for (int i = 0; i<snake.getArrPartSnake().size(); i++){
                if (rect.intersect(snake.getArrPartSnake().get(i).getrBody())){
                    check = true;
                    xy[0]=r.nextInt(arrGrass.size()-1);
                    xy[1]=r.nextInt(arrGrass.size()-1);
                    rect = new Rect(arrGrass.get(xy[0]).getX(), arrGrass.get(xy[1]).getY(),arrGrass.get(xy[0]).getX()+sizeOfMap, arrGrass.get(xy[1]).getY()+sizeOfMap);
                }
            }

        }
        return xy;
    }
}
