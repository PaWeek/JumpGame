package com.example.paweek.jjump;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.paweek.jjump.database.Result;
import com.example.paweek.jjump.database.ResultsAdapter;
import com.example.paweek.jjump.database.ResultsRepository;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity {

    private ResultsRepository resultsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        resultsRepository = new ResultsRepository(this);
        List<Result> results = resultsRepository.getTop25Results();
        ListAdapter adapter = new ResultsAdapter(this, results);
        ListView listResults = findViewById(R.id.listResults);
        listResults.setAdapter(adapter);
    }
}
