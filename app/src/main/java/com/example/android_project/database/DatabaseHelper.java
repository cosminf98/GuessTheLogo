package com.example.android_project.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.Environment;

import androidx.annotation.Nullable;

import com.example.android_project.models.Highscore;
import com.example.android_project.models.ImageItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "guessTheLogoDb";
    public static final String TABLE_HIGHSCORES = "highscores";
    public static final String TABLE_IMAGES= "images";

    public static final String IMG_image_name = "image_name";
    public static final String IMG_answer = "answer";

    public static final String HS_id="id";
    public static final String HS_name ="name";
    public static final String HS_score = "score";
    public static final String HS_time="time";


    AssetManager assetManager;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_HIGHSCORES+
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "score REAL," +
                "time TEXT);");

        db.execSQL("CREATE TABLE " + TABLE_IMAGES +
                " (image_name TEXT PRIMARY KEY," +
                "answer TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORES);

        onCreate(db);
        seedDatabase(db);
    }

    public void seedDatabase(SQLiteDatabase db)
    {
        this.addImage(db,new ImageItem("1.png","Mozilla Firefox"));
        this.addImage(db,new ImageItem("2.png","Premier League"));
        this.addImage(db,new ImageItem("3.png","Twitter"));
        this.addImage(db,new ImageItem("4.png","nVidia"));
        this.addImage(db,new ImageItem("5.png","Asics"));
        this.addImage(db,new ImageItem("6.png","Android"));
        this.addImage(db,new ImageItem("7.png","La Liga"));
        this.addImage(db,new ImageItem("8.png","PlayStation"));
        this.addImage(db,new ImageItem("9.png","Visual Studio"));
        this.addImage(db,new ImageItem("10.png","Warner Bros"));

    }

    //Highscores
    public void addHighscore(Highscore highscore)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HS_name,highscore.getName());
        values.put(HS_score,highscore.getScore());
        values.put(HS_time,highscore.getTime());

        db.insert(TABLE_HIGHSCORES,null,values);
        db.close();
    }

    public ArrayList<Highscore> getTopHighscores()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Highscore> highscoreList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_HIGHSCORES + " ORDER BY " + HS_score + " DESC" + " LIMIT 5";
        Cursor cursor = db.rawQuery(query,null);

        while(cursor.moveToNext())
        {
            Highscore hs = new Highscore(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getString(3));
            highscoreList.add(hs);
        }
        return highscoreList;
    }

    //IMAGES
    public void addImage(SQLiteDatabase db, ImageItem image)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(IMG_image_name, image.getImage_name());
        contentValues.put(IMG_answer,image.getAnswer());

        db.insert(TABLE_IMAGES,null,contentValues);
    }

    public ImageItem getImage(String image_name)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_IMAGES + " WHERE " + IMG_image_name +"=" + image_name;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null)
            cursor.moveToFirst();

        ImageItem image = new ImageItem(cursor.getString(0),cursor.getString(1));
        return image;
    }

    public List<ImageItem> getAllImages()
    {
        List <ImageItem> images = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_IMAGES;
        Cursor cursor = db.rawQuery(query,null);

        while(cursor.moveToNext())
        {
            ImageItem image = new ImageItem(cursor.getString(0),cursor.getString(1));
            images.add(image);
        }
        return images;
    }

}