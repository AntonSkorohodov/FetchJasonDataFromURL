package com.example.anton.urlfetchdata;

import android.location.Address;

import com.google.gson.Gson;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private com.example.anton.urlfetchdata.Address address;

    User (int id){
        this.id = id;
    }

    User(int id, String name, String username, String email, Address address, String phone, String website, Company company) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }

    User (String name,String username, String email,com.example.anton.urlfetchdata.Address address){
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
    }

    public User(int id, String name, String username, String email, com.example.anton.urlfetchdata.Address add) {
        this.id =id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = add;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress(){
        return address.getAddress();
    }


    @Override
    public String toString() {
        return getId()+ ", "+ getName()+", " +getUsername()+" " + getEmail() + ", "+ getAddress();
    }


    public String toJson(){
        return (new Gson()).toJson(this);
    }

    public static User fromJson(String json){
        return (new Gson()).fromJson(json,User.class);
    }
}
