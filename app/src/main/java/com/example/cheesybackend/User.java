package com.example.cheesybackend;

import org.json.JSONObject;

import java.util.ArrayList;

public class User {

    private String fname;

    private String lname;

    private String email;

    private String dob;

    private String address;

    private String zipcode;

    private String password;

    private int totalOrders;

    public User(){}
    public User(String fname, String lname, String email, String dob, String address, String zipcode, String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.dob = dob;
        this.address = address;
        this.zipcode = zipcode;
        this.password = password;
        totalOrders = 0;
    }
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
}
