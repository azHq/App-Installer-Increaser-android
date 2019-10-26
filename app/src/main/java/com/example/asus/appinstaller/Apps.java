package com.example.asus.appinstaller;

public class Apps {

    public String name;
    public String iconImage;
    public String totalInstalls;
    public String uploadDate;
    public Apps(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getTotalInstalls() {
        return totalInstalls;
    }

    public void setTotalInstalls(String totalInstalls) {
        this.totalInstalls = totalInstalls;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
