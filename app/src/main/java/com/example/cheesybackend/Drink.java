package com.example.cheesybackend;

import android.os.Parcel;
import android.os.Parcelable;

class Drink implements Parcelable {
    private String name;

    private double price;

    private String description;
    public Drink(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drink(String name, double price, String description) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    protected Drink(Parcel in) {
        name = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Drink> CREATOR = new Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
    }
}