package com.example.cheesybackend;

public class User {

    private String fname;


    private String lname;


    private String email;


    private String dob;


    private String address;


    private String zipcode;


    private String username;


    private String password;

    public User(){}
    public User(String fname, String lname, String email, String dob, String address, String zipcode, String username, String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.dob = dob;
        this.address = address;
        this.zipcode = zipcode;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
