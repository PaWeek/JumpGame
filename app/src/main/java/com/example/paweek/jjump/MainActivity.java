package com.example.paweek.jjump;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Warning");
                alertDialog.setMessage("You are currently in a battle");
                alertDialog.show();
                //goHowPlay();
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
