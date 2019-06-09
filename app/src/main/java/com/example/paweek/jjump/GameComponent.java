package com.example.paweek.jjump;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class GameComponent extends View {

    public TextView txtPoints;
    private Paint paint;
    private Integer jumpHeight, jumpPosition, substaclePosition, points;
    private Boolean goDown, goUp;
    public Boolean play;
    private Thread jumpThread, substaclesThread;

    public GameComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        points = 0;
        jumpHeight = 600;
        jumpPosition = 0;
        substaclePosition = 0;
        goDown = goUp = play = false;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        jumpThread = new Thread(new JumpThread(new Handler()));
        substaclesThread = new Thread(new RunThread(new Handler()));
        jumpThread.start();
        substaclesThread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(100, this.getHeight() - jumpPosition, 100, getHeight() - 200 - jumpPosition, paint);

        canvas.drawLine(this.getWidth() - substaclePosition, this.getHeight(), this.getWidth() - substaclePosition, getHeight() - 250, paint);
    }

    public void addTxt(TextView txt) {
        txtPoints = txt;
    }

    public void jump() {
        if (!goUp && !goDown)
            goUp = true;
    }

    public boolean isCrash() {
        return ((this.getWidth() - substaclePosition) == 100 && (getHeight() - jumpPosition) >= this.getHeight() - 200);
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
        if (!substaclePosition.equals(getWidth()))
            substaclePosition++;
        else
            substaclePosition = 1;

        if (substaclePosition == getWidth() - 1) {
            points++;
            txtPoints.setText(points.toString());
        }
    }

    private void addPoints() {
        for (int i = 1; i <= 10; i++) {
            if ((((this.getWidth()/10)*i)/(this.getWidth()-substaclePosition))== 1) {
                points++;
                txtPoints.setText(points.toString());
            }
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
        points = 0;
        txtPoints.setText(points.toString());
        substaclePosition = 1;
        jumpPosition = 0;
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
                    Thread.sleep(0,400000);

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
                    Thread.sleep(1);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (isCrash()) pauseGame();
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
