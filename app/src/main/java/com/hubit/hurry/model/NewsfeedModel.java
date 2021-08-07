package com.hubit.hurry.model;

public class NewsfeedModel {
    String title , image , desc , timestamp ;

    public NewsfeedModel() {
    }

    public NewsfeedModel(String title, String image, String desc, String timestamp) {
        this.title = title;
        this.image = image;
        this.desc = desc;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
