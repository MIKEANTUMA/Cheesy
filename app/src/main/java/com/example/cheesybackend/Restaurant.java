package com.example.cheesybackend;

import android.location.Address;
import android.media.Rating;

public class Restaurant {


    private String name;
    private Address location;
    private Menu menu;
    private int rating;
    private String phoneNumber;


    public Restaurant() { }

    public Restaurant(String name, Address location, Menu menu, int rating, String phoneNumber, String website) {
        this.name = name;
        this.location = location;
        this.menu = menu;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    private String website;




}
