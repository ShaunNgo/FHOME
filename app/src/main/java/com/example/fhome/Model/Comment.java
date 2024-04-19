package com.example.fhome.Model;

public class Comment {
    private int id;
    private String comment;
    private String blogID;
    private String userID;

    public Comment(int id, String comment, String blogID, String userID) {
        this.id = id;
        this.comment = comment;
        this.blogID = blogID;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBlogID() {
        return blogID;
    }

    public void setBlogID(String blogID) {
        this.blogID = blogID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
