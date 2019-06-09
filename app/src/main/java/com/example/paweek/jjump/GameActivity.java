package com.example.paweek.jjump;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    GameComponent game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        game = findViewById(R.id.jumpGameComponent);
        game.addTxt((TextView) findViewById(R.id.lblPoints));
    }

    public void gameClick(View view) {
        switch (view.getId()) {
            case R.id.jumpGameComponent:
                game.jump();
                break;
            case R.id.btnPausePlay:
                if (game.play) {
                    game.pauseGame();
                    ((Button)view).setText("play");
                }
                else {
                    game.startGame();
                    ((Button)view).setText("pause");
                }
                break;
            case R.id.btnRestart:
                game.restart();
                ((Button)findViewById(R.id.btnPausePlay)).setText("play");
                break;
        }
    }
}
