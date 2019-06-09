package com.example.paweek.jjump;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.btnMenuPlay:
                goPlay();
                break;
            case R.id.btnMenuRank:
                goRank();
                break;
            case R.id.btnMenuHowPlay:
                goHowPlay();
                break;
        }
    }

    void goPlay() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    void goRank() {
        Intent intent = new Intent(this, RankActivity.class);
        startActivity(intent);
    }

    void goHowPlay() {
        Intent intent = new Intent(this, HowToActivity.class);
        startActivity(intent);
    }
}
