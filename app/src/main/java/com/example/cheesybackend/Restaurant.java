package com.example.cheesybackend;

import android.location.Address;
import android.media.Rating;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Restaurant implements Parcelable {


    private String name;
    private String location;
    private Menu menu;
    private float rating;
    private String phoneNumber;
    private String website;


    public Restaurant(){}

    public Restaurant(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.location = restaurant.getLocation();
        this.menu = restaurant.getMenu();
        this.rating = restaurant.getRating();
        this.phoneNumber = restaurant.getPhoneNumber();
        this.website = restaurant.getWebsite();
    }
    public Restaurant(String name, String location, Menu menu, float rating, String phoneNumber, String website) {
        this.name = name;
        this.location = location;
        this.menu = menu;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }


    protected Restaurant(Parcel in) {
        name = in.readString();
        location = in.readString();
        rating = in.readFloat();
        phoneNumber = in.readString();
        website = in.readString();
        menu = (Menu) in.readParcelable(menu.getClass().getClassLoader());

    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(website);
        parcel.writeString(phoneNumber);
        parcel.writeParcelable(menu, i);
    }
}