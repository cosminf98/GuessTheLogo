package com.example.android_project.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.android_project.R;
import com.example.android_project.models.Highscore;

import java.util.ArrayList;
import java.util.List;

public class HighscoreAdapter extends ArrayAdapter<Highscore>
{
    Context mContext;
    int mResource;

    public HighscoreAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscore> objects) {
        super(context, resource, objects);
        mContext= context;
        mResource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get highscore info
        int id = getItem(position).getId();
        String mname = getItem(position).getName();
        String mscore = String.valueOf(getItem(position).getScore());
        String mtime = getItem(position).getTime();

        Highscore highscore = new Highscore(id,mname,Double.valueOf(mscore),mtime);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView name = (TextView) convertView.findViewById(R.id.namePlayer);
        TextView score = (TextView) convertView.findViewById(R.id.score);
        TextView time= (TextView) convertView.findViewById(R.id.scoretime);

        name.setText(mname);
        score.setText(mscore);
        time.setText(mtime);

        return convertView;
    }
}
