package com.example.asus.appinstaller;

public class PostDetails {

    public String userName,country,date,postDetails;

    public PostDetails(String userName, String country, String date, String postDetails) {
        this.userName = userName;
        this.country = country;
        this.date = date;
        this.postDetails = postDetails;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(String postDetails) {
        this.postDetails = postDetails;
    }
}
