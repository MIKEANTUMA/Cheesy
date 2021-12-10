package com.example.cheesybackend;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Cart {

    private static Cart single_instance = null;
    private ArrayList<Object> taskList = new ArrayList<>();
    private double total = 0;
    private double tip;
    private double tax;
    private int counter =0;

    HashMap<String, Double> receiptPrices;

    //     Thank you for your Order
    //        mikes famous pizza
    //            5161234556
    //     www.mikesfamouspizza.com
    //            date time
    //
    //
    //  item 1                    10.99
    //  item 2                    11.99
    //
    //                        Tax 2.99
    //                        tip 2.99
    //  total items: 2      total 30.99


    private Cart(){}

    public static Cart getInstance()
    {
        if (single_instance == null)
            single_instance = new Cart();

        return single_instance;
    }

    public Cart(ArrayList<Object> taskList, double total) {
        this.taskList = taskList;
        this.total = total;
    }

    public static Cart getSingle_instance() {
        return single_instance;
    }

    public static void setSingle_instance(Cart single_instance) {
        Cart.single_instance = single_instance;
    }

    public HashMap<String, Double> getReceiptPrices() {
        return receiptPrices;
    }

    public void setReceiptPrices(HashMap<String, Double> receiptPrices) {
        this.receiptPrices = receiptPrices;
    }

    public ArrayList<Object> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Object> taskList) {
        this.taskList = taskList;
    }

    public double getTotal() {
        return total;
    }

    //calculates the tip for the order
    private void CalculateTip(){
        this.tip = this.total*0.18;
        this.total+=this.tip;
    }

    //calculates tax for the bill
    //the tax rate is that of suffolk
    //county
    private void CalculateTax(){
        this.tax = this.total*0.08;
        this.total+=this.tax;
    }

    //calculates the total of the order
    private void CalculateTotal(){
        this.taskList.forEach((menuItem)-> {

            if (menuItem instanceof Drink) {
                Drink d = (Drink) menuItem;
                this.total += d.getPrice();
            } else if (menuItem instanceof Entree) {
                Entree e = (Entree) menuItem;
                this.total += e.getPrice();
            } else if (menuItem instanceof Appetizer) {
                Appetizer a = (Appetizer) menuItem;
                this.total += a.getPrice();
            } else {
                Log.d("ERROR CHECK OUT", "not valid object");
            }
        });
        this.CalculateTip();
        this.CalculateTax();
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    //adds item to cart
    public void addToCart(Object o){
        taskList.add(o);
    }

    //checks to see if items where added to cart
    //calls calculate total and creates receipt
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkOut(Context mCtx, Restaurant restaurant) throws JSONException {
        if(taskList.size() == 0){
            return;
        }
        this.CalculateTotal();
        this.createReceipt(mCtx,restaurant);
    }

    //creates a receipt as a json item
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createReceipt(Context mCtx, Restaurant restaurant) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Thank you","Thank you for your Order");
        jsonObject.put("name", restaurant.getName());
        jsonObject.put("phoneNumber", restaurant.getPhoneNumber());
        jsonObject.put("website", restaurant.getWebsite());
        Date currentTime = Calendar.getInstance().getTime();
        jsonObject.put("dateTime", String.valueOf(currentTime));

        this.taskList.forEach((menuItem)->{
            if(menuItem instanceof Drink)
            {
                Drink d = (Drink) menuItem;
                try {
                    jsonObject.put("item"+String.valueOf(counter), d.getName());
                    jsonObject.put(String.valueOf(counter), d.getPrice());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if(menuItem instanceof Entree)
            {
                Entree e = (Entree) menuItem;
                try {
                    jsonObject.put("item"+String.valueOf(counter), e.getName());
                    jsonObject.put(String.valueOf(counter), e.getPrice());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            else if(menuItem instanceof Appetizer)
            {
                Appetizer a = (Appetizer) menuItem;
                try {
                    jsonObject.put("item"+String.valueOf(counter), a.getName());
                    jsonObject.put(String.valueOf(counter), a.getPrice());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                Log.d("ERROR CHECK OUT", "not valid object");
            }
            counter++;
        });
        jsonObject.put("tax",this.tax);
        jsonObject.put("tip", this.tip);
        jsonObject.put("totalItems", this.taskList.size());
        jsonObject.put("totalPrice", this.total);

        Intent intent = new Intent(mCtx, Checkout.class);
        String jobj = jsonObject.toString();
        intent.putExtra("receipt",jobj);
        intent.putExtra("itemAmount", counter);
        mCtx.startActivity(intent);
        //     Thank you for your Order
        //        mikes famous pizza
        //            5161234556
        //     www.mikesfamouspizza.com
        //            date time
        //
        //
        //  item 1                    10.99
        //  item 2                    11.99
        //
        //                        Tax 2.99
        //                        tip 2.99
        //  total items: 2      total 30.99
        //
        //
        // delivery to 175 earl place north
    }
}
