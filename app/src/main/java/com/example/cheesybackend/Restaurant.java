package com.example.cheesybackend;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.firebase.firestore.GeoPoint;

public class Restaurant  implements Parcelable {


    private String name;
    private String location;
    private Menu menu;
    private float rating;
    private String phoneNumber;
    private String website;
    private String description;
    private String geohash;
    private GeoPoint geoPoint;

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Restaurant(){}

    public String getGeohash() {
        return geohash;
    }


    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public Restaurant(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.location = restaurant.getLocation();
        this.menu = restaurant.getMenu();
        this.rating = restaurant.getRating();
        this.phoneNumber = restaurant.getPhoneNumber();
        this.website = restaurant.getWebsite();
        this.description = restaurant.getDescription();

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Restaurant(String name, String location, Menu menu, float rating, String phoneNumber, String website, String description) {
        this.name = name;
        this.location = location;
        this.menu = menu;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.description = description;

    }


    protected Restaurant(Parcel in) {
        name = in.readString();
        location = in.readString();
        rating = in.readFloat();
        menu = in.readParcelable(Menu.class.getClassLoader());
        phoneNumber = in.readString();
        website = in.readString();
        description = in.readString();

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(location);
        dest.writeFloat(rating);
        dest.writeParcelable(menu, flags);
        dest.writeString(phoneNumber);
        dest.writeString(website);
        dest.writeString(description);

    }


}