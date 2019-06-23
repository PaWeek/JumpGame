package com.example.paweek.jjump;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paweek.jjump.database.Result;
import com.example.paweek.jjump.database.ResultsRepository;

import static android.support.v7.app.AlertDialog.*;

public class GameActivity extends AppCompatActivity implements Observer {

    private GameComponent game;
    private TextView lblPoints;
    private ResultsRepository resultsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        resultsRepository = new ResultsRepository(this);
        lblPoints = findViewById(R.id.lblPoints);
        game = findViewById(R.id.jumpGameComponent);
        game.addTxt(lblPoints);
        game.registerObserver(this);
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

    @Override
    public void update(Observable observable, Object args) {
        Integer points = Integer.parseInt(lblPoints.getText().toString());
        Result result = resultsRepository.getBestResult();
        if (result != null && points > result.getPoints())
            showSaveScoreDialog();
    }

    private void showSaveScoreDialog() {
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_score_dialog, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        final TextView lblResult = view.findViewById(R.id.lblResult);
        final EditText txtNickname = view.findViewById(R.id.txtNickname);
        Integer points = Integer.parseInt(lblPoints.getText().toString());
        lblResult.setText(points.toString() + (points == 0 ? " point ! " : " points ! ") + lblResult.getText().toString());
        Button btnSaveNickname = view.findViewById(R.id.btnSaveNickname);
        btnSaveNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = txtNickname.getText().toString();
                if (!nickName.trim().isEmpty()) {
                    Integer points = Integer.parseInt(lblPoints.getText().toString());
                    resultsRepository.saveResult(new Result(nickName.trim(), points));
                    dialog.dismiss();
                    game.restart();
                } else {
                    Toast.makeText(context, "Name can't be empty!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
