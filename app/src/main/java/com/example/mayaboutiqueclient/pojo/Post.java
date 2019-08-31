package com.example.mayaboutiqueclient.pojo;

import java.io.Serializable;

public class Post implements Serializable {
    private String Date;
   private String Time;
   private String desc_image;
   private String post_image;


    public Post() {
    }

    public Post(String date, String time, String desc_image, String post_image) {
        this.Date = date;
        this.Time = time;
        this.desc_image = desc_image;
        this.post_image = post_image;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDesc_image() {
        return desc_image;
    }

    public void setDesc_image(String desc_image) {
        this.desc_image = desc_image;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }
}