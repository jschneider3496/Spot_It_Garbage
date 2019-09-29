package com.example.spot_itgarbage;

public class MarkerData {

    private String desc;
    private int rating;
    private double lat;
    private double lng;

    public MarkerData(String desc, int rating, double lat, double lng) {
        this.desc = desc;
        this.rating = rating;
        this.lat = lat;
        this.lng = lng;
    }

    public MarkerData(){

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
