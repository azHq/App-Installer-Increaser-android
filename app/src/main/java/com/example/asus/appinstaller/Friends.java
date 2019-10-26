package com.example.asus.appinstaller;

public class Friends {

    private String userName;
   private String thumb_image;
    private boolean Activity;

    public boolean isActivity() {
        return Activity;
    }

    public void setActivity(boolean Activity) {
        this.Activity = Activity;
    }

    public Friends(){

    }

    public Friends(String userName){

        this.userName=userName;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }
}
