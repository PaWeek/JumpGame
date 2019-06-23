package com.example.paweek.jjump.database;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.paweek.jjump.R;

import java.util.ArrayList;
import java.util.List;

public class ResultsAdapter extends ArrayAdapter<Result> {

    private List<Result> results;

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            row = inflater.inflate(R.layout.layout_list_scores_row, null);

            holder = new ResultHolder();
            holder.nickName = row.findViewById(R.id.listNickname);
            holder.points = row.findViewById(R.id.listPoints);

            row.setTag(holder);
        } else {
            holder = (ResultHolder) row.getTag();
        }

        holder.points.setText(results.get(position).getPoints().toString());
        holder.nickName.setText((position + 1) + ". " + results.get(position).getPlayer() + " :");

        return row;
    }

    public ResultsAdapter(Context context, List<Result> results) {
        super(context, R.layout.layout_list_scores_row);
        this.results = results;
    }

    static class ResultHolder {
        TextView nickName;
        TextView points;
    }


}
