package com.example.android_project.models;

public class Highscore
{
    private int id;
    private String name;
    private double score;
    private String time;

    public Highscore(int id, String name, double score, String time) {
        this.id=id;
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public Highscore(String name, double score, String time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }




    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
