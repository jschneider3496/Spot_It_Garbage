package com.example.spot_itgarbage;

import java.io.Serializable;

public class MarkerData implements Serializable {

    private String desc;
    private int rating;
    private double lat;
    private double lng;
    private String url;

    public MarkerData(String desc, int rating, double lat, double lng, String url) {
        this.desc = desc;
        this.rating = rating;
        this.lat = lat;
        this.lng = lng;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MarkerData(){
        this.desc = "Default description";
        this.rating = 0;
        this.lat = 39.0119;
        this.lng = 98.4842;
        this.url = "https://firebasestorage.googleapis.com/v0/b/grounded-region-254319.appspot.com/o/documentImages%2F20191102_150613?alt=media&token=6a2b20b0-4776-4604-abee-b9665199daf1";
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toString(){
        return desc;
    }
}
