package com.example.cheesybackend;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Menu implements Parcelable {

    private List<Entree> entree;

    private List<Drink> drinks;

    private List<Appetizer> appetizer;

    protected Menu(Parcel in) {
        entree = in.createTypedArrayList(Entree.CREATOR);
        drinks = in.createTypedArrayList(Drink.CREATOR);
        appetizer = in.createTypedArrayList(Appetizer.CREATOR);
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public List<Entree> getEntree() {
        return entree;
    }

    public void setEntree(List<Entree> entree) {
        this.entree = entree;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public List<Appetizer> getAppetizer() {
        return appetizer;
    }

    public void setAppetizer(List<Appetizer> appetizer) {
        this.appetizer = appetizer;
    }

    public Menu(){}

    public Menu(List<Entree> entree, List<Drink> drinks, List<Appetizer> appetizer) {
        this.entree = entree;
        this.drinks = drinks;
        this.appetizer = appetizer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(entree);
        parcel.writeTypedList(drinks);
        parcel.writeTypedList(appetizer);
    }

}

class Entree implements Parcelable {
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeDouble(price);
    }
}

class Drink implements Parcelable {
    private String name;

    private double price;

    public Drink(){}

    public Drink(String name, double price) {
        this.name = name;

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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(price);
    }
}

class Appetizer implements Parcelable {
    private String name;
    private String description;
    private double price;

    public Appetizer(){}

    public Appetizer(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    protected Appetizer(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Appetizer> CREATOR = new Creator<Appetizer>() {
        @Override
        public Appetizer createFromParcel(Parcel in) {
            return new Appetizer(in);
        }

        @Override
        public Appetizer[] newArray(int size) {
            return new Appetizer[size];
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeDouble(price);
    }
}