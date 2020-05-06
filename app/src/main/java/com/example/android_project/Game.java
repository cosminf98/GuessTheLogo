package com.example.android_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.database.DatabaseHelper;
import com.example.android_project.models.Highscore;
import com.example.android_project.models.ImageItem;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Game extends AppCompatActivity {

    double points=0;
    String player;

    ImageView imageView;
    ImageItem currentImage;
    TextView textHint;
    Button btnGuess;
    Button btnGoBack;
    List<ImageItem> images;
    EditText textGuess;
    DatabaseHelper db = new DatabaseHelper(this);
    int currentIndex=0;
    long timeElapsed;


    public static final String FOLDER_PATH="logos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imageView = (ImageView) findViewById(R.id.imageLogo);
        btnGuess = (Button) findViewById(R.id.btnGuess);
        textGuess = (EditText) findViewById(R.id.textGuess);
        textHint = (TextView) findViewById(R.id.textHint);
        addBtnGuessListener();
        images = db.getAllImages();
        Collections.shuffle(images);

        currentImage = images.get(0);
        setImage(currentImage.getImage_name());
        setHint();
        timeElapsed = System.currentTimeMillis();

        btnGoBack = (Button) findViewById(R.id.btnGameBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void setImage(String imageName)
    {
        try
        {
            InputStream imageStream = getAssets().open(FOLDER_PATH + "/" + imageName );
            Drawable d = Drawable.createFromStream(imageStream, null);
            imageView.setImageDrawable(d);
            imageStream.close();
        }
        catch(IOException ex)
        {
            Log.d("EXCEPTION","Set image");
            return;
        }
    }

    public void addBtnGuessListener()
    {
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    guess();
                    setNextPhoto();
                } catch (Exception e) { // game ended
                    //ask for name, add to highscores, go back
                    openGameFinishedDialog();
                }
            }
        });
    }

    public void guess()
    {
        String guess = textGuess.getText().toString();
        if(guess.toLowerCase().equals(currentImage.getAnswer().toLowerCase()))
        {
            points++;
            Toast.makeText(Game.this,"Correct!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(Game.this,"Wrong!",Toast.LENGTH_SHORT).show();
        }
        textGuess.setText("");
    }

    public void setNextPhoto() throws Exception
    {
        currentIndex++;
        currentImage = images.get(currentIndex);
        setHint();
        setImage(currentImage.getImage_name());
    }

    public void openGameFinishedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You scored " + String.valueOf(points) + " points!");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Player Name");
        builder.setView(input);

        // Set up the buttons
        timeElapsed = (System.currentTimeMillis() - timeElapsed)/1000;

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player= input.getText().toString();
                if(!player.equals(""))
                {
                    //add to highscores
                    db.addHighscore(new Highscore(player,points,String.valueOf(timeElapsed)));
                    //go back to main menu
                    Game.super.onBackPressed();
                }
                else
                    Toast.makeText(Game.this,"Player Name must not be empty!",Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private int countChars(String s)
    {
        int count =1;
        for(int i=0;i<s.length();i++)
            if (s.charAt(i)==' ') count++;
        return count;
    }
    private void setHint()
    {
        int count = countChars(currentImage.getAnswer());
        if(count ==1) textHint.setText("1 word");
        else textHint.setText(count + " words");
    }
}
