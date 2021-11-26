package com.example.cheesybackend;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Menu implements Parcelable {

    private ArrayList<Entree> entree;

    private ArrayList<Drink> drinks;

    private ArrayList<Appetizer> appetizer;


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

    public ArrayList<Entree> getEntree() {

        return entree;
    }

    public void setEntree(ArrayList<Entree> entree) {
        this.entree = entree;
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<Drink> drinks) {
        this.drinks = drinks;
    }

    public ArrayList<Appetizer> getAppetizer() {
        return appetizer;
    }

    public void setAppetizer(ArrayList<Appetizer> appetizer) {
        this.appetizer = appetizer;
    }

    public Menu(){}

    public Menu(ArrayList<Entree> entree, ArrayList<Drink> drinks, ArrayList<Appetizer> appetizer) {
        this.entree = entree;
        this.drinks = drinks;
        this.appetizer = appetizer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(entree);
        dest.writeTypedList(drinks);
        dest.writeTypedList(appetizer);
    }
}










