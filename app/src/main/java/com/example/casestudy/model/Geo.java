package com.example.casestudy.model;

import com.google.gson.Gson;

public class Geo {
    private Float lat;
    private Float lng;

    public Geo(Float lat, Float lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String toJson(){
        return (new Gson()).toJson(this);
    }

    public static Geo fromJson(String json){
        return (new Gson()).fromJson(json, Geo.class);
    }

    public String getGeo() {
        return "Lat: "+ getLat() + ", Lng: " +getLng();
    }
}
