package com.example.tic_tac_toe_kotlin_firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.MotionEvent;
import android.widget.Switch;
import android.widget.TextView;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nullable;

public class SnakeGameView extends View {
    private Bitmap bmGrass1, bmGrass2, bmSnake, bmApple;
    public static int sizeOfMap = 75 * ConstantsSnake.SCREEN_WIDTH / 1080;
    private int h = 21, w = 12;
    private ArrayList<SnakeGrass> arrGrass = new ArrayList<>();
    private Snake snake;
    private boolean move = false;
    private float mx, my;
    private Handler handler;
    private Runnable r;
    private Apple apple;
    public int score = 0, bestScore = 0;
    private Context context;
    public static boolean isPlaying = false;
    private Paint textPaint;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    public SnakeGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        sharedPreferences = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences != null) {
            bestScore = sharedPreferences.getInt("bestscore", 0);
        }
        bmGrass1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        bmGrass1 = Bitmap.createScaledBitmap(bmGrass1, sizeOfMap, sizeOfMap, true);
        bmGrass2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass03);
        bmGrass2 = Bitmap.createScaledBitmap(bmGrass2, sizeOfMap, sizeOfMap, true);
        bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake1);
        bmSnake = Bitmap.createScaledBitmap(bmSnake, 14 * sizeOfMap, sizeOfMap, true);
        bmApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        bmApple = Bitmap.createScaledBitmap(bmApple, sizeOfMap, sizeOfMap, true);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if ((j + i) % 2 == 0) {
                    arrGrass.add(new SnakeGrass(bmGrass1, j * bmGrass1.getWidth() + ConstantsSnake.SCREEN_WIDTH / 2 - (w / 2) * bmGrass1.getWidth(), i * bmGrass1.getHeight() + 50 * ConstantsSnake.SCREEN_HEIGHT / 1920, bmGrass1.getWidth(), bmGrass1.getHeight()));
                } else {
                    arrGrass.add(new SnakeGrass(bmGrass2, j * bmGrass2.getWidth() + ConstantsSnake.SCREEN_WIDTH / 2 - (w / 2) * bmGrass2.getWidth(), i * bmGrass2.getHeight() + 50 * ConstantsSnake.SCREEN_HEIGHT / 1920, bmGrass2.getWidth(), bmGrass2.getHeight()));
                }
            }
        }
        snake = new Snake(bmSnake, arrGrass.get(126).getX(), arrGrass.get(126).getY(), 4);
        apple = new Apple(bmApple, arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        //text dla highscore i score
        textPaint = new Paint();
        textPaint.setColor(0xFF000000);
        textPaint.setTextSize(100);
        textPaint.setAntiAlias(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();
        switch (a) {
            case MotionEvent.ACTION_MOVE: {
                if (!move) {
                    mx = event.getX();
                    my = event.getY();
                    move = true;
                } else {
                    if (mx - event.getX() > 100 && !snake.isMove_right()) {
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_left(true);
                        isPlaying = true;
                    } else if (event.getX() - mx > 100 && !snake.isMove_left()) {
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_right(true);
                        isPlaying = true;
                    } else if (event.getY() - my > 100 && !snake.isMove_top()) {
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_bottom(true);
                        isPlaying = true;
                    } else if (my - event.getY() > 100 && !snake.isMove_bottom()) {
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_top(true);
                        isPlaying = true;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                move = false;
                break;
            }
        }
        return true;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(0xFFECE5D5);
        for (int i = 0; i < arrGrass.size(); i++) {
            canvas.drawBitmap(arrGrass.get(i).getBm(), arrGrass.get(i).getX(), arrGrass.get(i).getY(), null);
        }
        if (isPlaying) {
            snake.update();
            if (snake.getArrPartSnake().get(0).getX() < this.arrGrass.get(0).getX()
                    || snake.getArrPartSnake().get(0).getY() < this.arrGrass.get(0).getY()
                    || snake.getArrPartSnake().get(0).getY() + sizeOfMap > this.arrGrass.get(this.arrGrass.size() - 1).getY() + sizeOfMap
                    || snake.getArrPartSnake().get(0).getX() + sizeOfMap > this.arrGrass.get(this.arrGrass.size() - 1).getX() + sizeOfMap) {
                gameOver();
            }
            for (int i = 1; i < snake.getArrPartSnake().size(); i++) {
                if (snake.getArrPartSnake().get(0).getrBody().intersect(snake.getArrPartSnake().get(i).getrBody())) {
                    gameOver();
                }
            }
        }
        snake.draw(canvas);
        apple.draw(canvas);
        if (snake.getArrPartSnake().get(0).getrBody().intersect(apple.getR())) {
            randomApple();
            apple.reset(arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
            snake.addPart();
            score++;
            if (score > bestScore) {
                bestScore = score;
            }
        }
        int x = 100;
        int y = canvas.getHeight() - 150;
        canvas.drawText("" + score + "x ", x, y, textPaint);
        int xbestscore = canvas.getWidth() - 210;
        canvas.drawText("x" + bestScore, xbestscore, y, textPaint);
        handler.postDelayed(r, 170); // Зменшено затримку для частішого оновлення
    }


    public int[] randomApple() {
        int[] xy = new int[2];
        Random r = new Random();
        xy[0] = r.nextInt(arrGrass.size() - 1);
        xy[1] = r.nextInt(arrGrass.size() - 1);
        Rect rect = new Rect(arrGrass.get(xy[0]).getX(), arrGrass.get(xy[1]).getY(), arrGrass.get(xy[0]).getX() + sizeOfMap, arrGrass.get(xy[1]).getY() + sizeOfMap);
        boolean check = true;
        while (check) {
            check = false;
            for (int i = 0; i < snake.getArrPartSnake().size(); i++) {
                if (rect.intersect(snake.getArrPartSnake().get(i).getrBody())) {
                    check = true;
                    xy[0] = r.nextInt(arrGrass.size() - 1);
                    xy[1] = r.nextInt(arrGrass.size() - 1);
                    rect = new Rect(arrGrass.get(xy[0]).getX(), arrGrass.get(xy[1]).getY(), arrGrass.get(xy[0]).getX() + sizeOfMap, arrGrass.get(xy[1]).getY() + sizeOfMap);
                }
            }

        }
        return xy;
    }

    private void gameOver() {
        isPlaying = false;
        reset();
        score = 0;
        editor.putInt("bestscore", bestScore);
        editor.apply();
    }

    public void reset() {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if ((j + i) % 2 == 0) {
                    arrGrass.add(new SnakeGrass(bmGrass1, j * bmGrass1.getWidth() + ConstantsSnake.SCREEN_WIDTH / 2 - (w / 2) * bmGrass1.getWidth(), i * bmGrass1.getHeight() + 50 * ConstantsSnake.SCREEN_HEIGHT / 1920, bmGrass1.getWidth(), bmGrass1.getHeight()));
                } else {
                    arrGrass.add(new SnakeGrass(bmGrass2, j * bmGrass2.getWidth() + ConstantsSnake.SCREEN_WIDTH / 2 - (w / 2) * bmGrass2.getWidth(), i * bmGrass2.getHeight() + 50 * ConstantsSnake.SCREEN_HEIGHT / 1920, bmGrass2.getWidth(), bmGrass2.getHeight()));
                }
            }
        }
        snake = new Snake(bmSnake, arrGrass.get(126).getX(), arrGrass.get(126).getY(), 4);
        apple = new Apple(bmApple, arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
    }

}
