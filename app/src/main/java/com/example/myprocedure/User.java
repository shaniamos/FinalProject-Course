package com.example.myprocedure;

public class User {
    String First_name;
    String Last_name;
    String Picture_link;

    public User(String first_name, String last_name, String picture_link) {
        First_name = first_name;
        Last_name = last_name;
        Picture_link = picture_link;
    }

    public User(){

    }

    public String getFirst_name() {
        return First_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public String getPicture_link() {
        return Picture_link;
    }

    public void setFirst_name(String first_name) {
        First_name = first_name;
    }

    public void setLast_name(String last_name) {
        Last_name = last_name;
    }

    public void setPicture_link(String picture_link) {
        Picture_link = picture_link;
    }
}
