package com.example.cheesybackend;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    private static Cart single_instance = null;
    private ArrayList<Object> taskList = new ArrayList<>();
    private double total = 0;
    private double tip;
    private double tax;


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

    private void CalculateTip(){
        tip = total*0.18;
    }

    private void CalculateTax(){
        tax = total*0.08;
    }

    private void CalculateTotal(){
        for (int i =0; i<0; i++)
        {
            if(taskList.get(i).equals(Drink.class))
            {
                Drink d = (Drink) taskList.get(i);
                total+=d.getPrice();
            }
            else if(taskList.get(i).equals(Entree.class))
            {
                Entree e = (Entree) taskList.get(i);
                total+=e.getPrice();
            }
            else if(taskList.get(i).equals(Appetizer.class))
            {
                Appetizer a = (Appetizer) taskList.get(i);
                total+=a.getPrice();
            }
            else{
                Log.d("ERROR CHECK OUT", "not valid object");
            }
        }
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

    public void addToCart(Object o){
        taskList.add(o);
        Log.d("CART", "added to cart");



    }

//    public JSONArray JSONcreateJsonArray(){
//
//        JSONArray itemList;
//        for(int i = 0; i < taskList.size(); i++){
//
//            if(taskList.get(i).equals(Drink.class))
//            {
//
//            }
//            else if(taskList.get(i).equals(Entree.class))
//            {
//
//            }
//            else if(taskList.get(i).equals(Appetizer.class))
//            {
//
//            }
//            else{
//                Log.d("ERROR CHECK OUT", "not valid object");
//            }
//        }
//       // return itemList;
//    }

    public void checkOut(Context mCtx){
        if(taskList.size() == 0){
            return;
        }
        this.CalculateTotal();
        this.createReceipt(mCtx);
    }

    private void createReceipt(Context mCtx) {
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
