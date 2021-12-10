package com.example.cheesybackend;

import android.os.Parcel;
import android.os.Parcelable;

class Entree  implements Parcelable {
    private String name;
    private String description;
    private double price;

    public Entree(){}

    public Entree(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    protected Entree(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Entree> CREATOR = new Creator<Entree>() {
        @Override
        public Entree createFromParcel(Parcel in) {
            return new Entree(in);
        }

        @Override
        public Entree[] newArray(int size) {
            return new Entree[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        dest.writeString(description);
        dest.writeDouble(price);
    }
}