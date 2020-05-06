package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android_project.database.DatabaseHelper;
import com.example.android_project.models.Highscore;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

    Button btnNewGame;
    Button btnHighscores;
    Button btnShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Game.class);
                startActivity(intent);
            }
        });

        btnHighscores = (Button) findViewById(R.id.btnHighScores);
        btnHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,Highscores.class);
                startActivity(intent);
            }
        });

        btnShare = (Button) findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(MainMenu.this);
                ArrayList<Highscore> highscores = db.getTopHighscores();

                String highscoresString = "My top scores on GuessTheLogo!\n";
                for(Highscore hs : highscores)
                {
                    highscoresString+= hs.getName() + ": " + hs.getScore() + " in " + hs.getTime() + "s\n";
                }
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, highscoresString);
                intent.setType("text/plain");

                startActivity(Intent.createChooser(intent, "Share"));
            }
        });



    }


}