package com.example.casestudy.model;

import com.google.gson.Gson;

//FIXME : Foot massage ? Or body massage ?
public class Message {
    String massage_id;
    String massage_body;
    String massage_title;

    public Message(String massage_id, String massage_body, String massage_title) {
        setMassage_body(massage_id);
        setMassage_body(massage_body);
        setMassage_title(massage_title);
    }


    public String toJson(){
        return (new Gson()).toJson(this);
    }

    public static User fromJson(String json){
        return (new Gson()).fromJson(json,User.class);
    }

    public String getMassage_id() {
        return massage_id;
    }

    public void setMassage_id(String massage_id) {
        this.massage_id = massage_id;
    }

    public String getMassage_body() {
        return massage_body;
    }

    public void setMassage_body(String massage_body) {
        this.massage_body = massage_body;
    }

    public String getMassage_title() {
        return massage_title;
    }

    public void setMassage_title(String massage_title) {
        this.massage_title = massage_title;
    }
}
