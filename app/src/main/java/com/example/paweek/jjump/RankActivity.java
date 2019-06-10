package com.example.paweek.jjump;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.paweek.jjump.database.Result;
import com.example.paweek.jjump.database.ResultsRepository;

public class RankActivity extends AppCompatActivity {

    private ResultsRepository resultsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        resultsRepository = new ResultsRepository(this);
        //resultsRepository.saveResult(new Result("AAA", 15));
        Result result = resultsRepository.getBestResult();
        TextView txtResult = findViewById(R.id.lblResult);
        if (result != null)
            txtResult.setText(result.getPlayer() + result.getPoints().toString());
    }
}
