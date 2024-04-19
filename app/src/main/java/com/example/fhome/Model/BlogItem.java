package com.example.fhome.Model;

public class BlogItem {
    private int id;
    private String userID;
    private String title;
    private String content;
    private long timestamp;

    public BlogItem(){}
    public BlogItem(int id, String userID, String title, String content) {
        this.id = id;
        this.userID = userID;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {return userID;}

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getTimestamp() {return timestamp;}
}
