package com.netmart.netmartseller.Model;

public class history {

    String image_url, actionhistory, desc, date;

    public history(){

    }

    public history(String image_url, String actionhistory, String desc, String date) {
        this.image_url = image_url;
        this.actionhistory = actionhistory;
        this.desc = desc;
        this.date = date;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getActionhistory() {
        return actionhistory;
    }

    public void setActionhistory(String actionhistory) {
        this.actionhistory = actionhistory;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
