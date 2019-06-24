package com.example.paweek.jjump;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameComponent extends View implements com.example.paweek.jjump.Observable {

    private Context context;
    public TextView txtPoints;
    private Paint paint;
    private Integer jumpHeight, jumpPosition, substaclePosition, points, nanosSpeed;
    private Boolean goDown, goUp, notified;
    public Boolean play;
    private Thread jumpThread, substaclesThread;
    private List<Observer> observers;
    private Bitmap treeBitmap;
    private MediaPlayer jumpSound, crashSound;

    public GameComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        observers = new ArrayList();
        nanosSpeed = 900000;
        points = 0;
        jumpHeight = 450;
        jumpPosition = 0;
        substaclePosition = 0;
        goDown = goUp = play = notified = false;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        jumpThread = new Thread(new JumpThread(new Handler()));
        substaclesThread = new Thread(new RunThread(new Handler()));
        treeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kaktus);
        jumpSound = MediaPlayer.create(context, R.raw.jump);
        crashSound = MediaPlayer.create(context, R.raw.crash);
        jumpThread.start();
        substaclesThread.start();
    }

    private void changeSpeed() {
        if (points % 2 == 0) {
            nanosSpeed = nanosSpeed - 20000;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(300, this.getHeight() - 130 - jumpPosition, 55, paint);
        canvas.drawBitmap(treeBitmap, this.getWidth() - substaclePosition, getHeight() - 270, paint);
        canvas.drawLine(0, getHeight() - 100, getWidth(), getHeight() - 100, paint);
    }

    public void addTxt(TextView txt) {
        txtPoints = txt;
    }

    public void jump() {
        if (!play) return;

        if (!goUp && !goDown) {
            jumpSound.start();
            goUp = true;
        }
    }

    public boolean isCrash() {
        return (((this.getWidth() - substaclePosition) <= 345 && (this.getWidth() - substaclePosition) >= 190) && (getHeight() - jumpPosition) >= this.getHeight() - 180);
    }

    private void jumpLogic() {
        if (!play) return;
        if (goUp) {
            jumpPosition ++;
            if (jumpPosition.equals(jumpHeight)) {
                goUp = false;
                goDown = true;
            }
        }

        if (goDown) {
            jumpPosition --;

            if (jumpPosition.equals(0))
                goDown = false;
        }
    }

    private void substaclesLogic() {
        if (!play) return;
        if (!substaclePosition.equals(getWidth() + 200))
            substaclePosition++;
        else
            substaclePosition = 1;

        if (substaclePosition == getWidth() - 1) {
            points++;
            txtPoints.setText(points.toString());
            changeSpeed();
        }
    }

    public void pauseGame() {
        play = false;
    }

    public void startGame() {
        play = true;
    }

    public void restart() {
        pauseGame();
        goDown = goUp = notified = false;
        points = 0;
        txtPoints.setText(points.toString());
        substaclePosition = 1;
        nanosSpeed = 900000;
        jumpPosition = 0;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object args) {
        for(Observer observer : observers)
            observer.update(this, args);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers)
            observer.update(this, null);
    }

    class JumpThread implements Runnable {
        private Handler handler;

        public JumpThread(Handler h)
        {
            handler = h;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!isCrash())
                                jumpLogic();
                        }
                    });

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    class RunThread implements Runnable {
        private Handler handler;

        public RunThread(Handler h)
        {
            handler = h;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(0, nanosSpeed);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (isCrash()) {
                                pauseGame();
                                if (!notified) {
                                    crashSound.start();
                                    notifyObservers();
                                    notified = true;
                                }
                            }
                            substaclesLogic();
                            invalidate();
                        }
                    });

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
