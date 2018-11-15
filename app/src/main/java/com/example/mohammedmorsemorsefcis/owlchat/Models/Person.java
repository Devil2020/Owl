package com.example.mohammedmorsemorsefcis.owlchat.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    private String Name ;
    private   String ImageUrl;


}
