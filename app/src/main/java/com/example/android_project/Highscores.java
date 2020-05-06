package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.android_project.adapters.HighscoreAdapter;
import com.example.android_project.database.DatabaseHelper;
import com.example.android_project.models.Highscore;
import com.example.android_project.models.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class Highscores extends AppCompatActivity {

    ListView highscoresView;
    ArrayList<Highscore> highscoresTop = new ArrayList<>();
    HighscoreAdapter adapter;
    DatabaseHelper db = new DatabaseHelper(this);
    Button btnGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        highscoresView = (ListView) findViewById(R.id.highscoresList);
        btnGoBack = (Button) findViewById(R.id.btnHighscoresBack);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        populateHighscores();
    }

    private void populateHighscores(){
        highscoresTop = db.getTopHighscores();
        adapter = new HighscoreAdapter(this,R.layout.highscore_item,highscoresTop);
        highscoresView.setAdapter(adapter);
    }

}
