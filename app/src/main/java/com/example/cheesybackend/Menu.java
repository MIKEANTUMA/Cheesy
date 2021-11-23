package com.example.cheesybackend;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

    private List<Entree> entree;

    private List<Drink> drinks;

    private List<Appetizer> appetizer;

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

    public static class Entree extends Menu {
        private String name;
        private String description;
        private double price;

        public Entree(){}

        public Entree(String name, String description, double price) {
            this.name = name;
            this.description = description;
            this.price = price;
        }

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
    }

    public static class Drink extends Menu {
        private String name;

        private double price;

        public Drink(){}

        public Drink(String name, double price) {
            this.name = name;

            this.price = price;
        }
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
    }

    public static class Appetizer extends Menu {
        private String name;
        private String description;
        private double price;

        public Appetizer(){}

        public Appetizer(String name, String description, double price) {
            this.name = name;
            this.description = description;
            this.price = price;
        }
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
    }
}
