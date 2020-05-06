package com.example.android_project.models;

public class ImageItem
{
    private String image_name;
    private String answer;

    public ImageItem(String image_name, String answer) {
        this.image_name = image_name;
        this.answer = answer;
    }


    public String getImage_name() {return image_name;}

    public void setImage_name(String image_name) { this.image_name = image_name;}

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
