package com.example.cheesybackend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {

    LinearLayout.LayoutParams etParas;
    JSONObject jObj;
    int itemAmount;
    ArrayList<TextView> views;
    LinearLayout ll;
    TextView name;
    TextView phoneNumber;
    TextView website;
    TextView dateTime;
    TextView tv_itemName;
    TextView tv_itemPrice;
    TextView tv_tax;
    TextView tv_tip;
    TextView tv_totalItem;
    TextView tv_total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ll=findViewById(R.id.myMainLayout);
        name = findViewById(R.id.tv_name);
        phoneNumber = findViewById(R.id.tv_phoneNumber);
        website = findViewById(R.id.tv_website);
        dateTime = findViewById(R.id.tv_dateTime);
        views = new ArrayList();
        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("receipt");
        itemAmount = intent.getIntExtra("itemAmount", 0);
        try {
            jObj = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        etParas=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.HORIZONTAL
        );
        try {
            populateReceipt();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void populateReceipt() throws JSONException {
        name.setText( jObj.getString("name"));
        phoneNumber.setText( jObj.getString("phoneNumber"));
        website.setText( jObj.getString("website"));
        dateTime.setText(jObj.getString("dateTime"));

        for(int i = 0; i<itemAmount;i++){
            Log.d("KEY",jObj.getString(String.valueOf(i)));
            tv_itemName= new TextView(this);
            tv_itemPrice = new TextView(this);
            tv_itemName.setText(jObj.getString("item"+String.valueOf(i)));
            tv_itemPrice.setText(jObj.getString(String.valueOf(i)));
            views.add(tv_itemName);
            views.add(tv_itemPrice);
            ll.addView(tv_itemName);
            ll.addView(tv_itemPrice);
        }
        tv_tax = new TextView(this);
        tv_tip = new TextView(this);
        tv_total = new TextView(this);
        tv_totalItem = new TextView(this);
        tv_total = new TextView(this);
        tv_tax.setText("Tax "+jObj.get("tax"));
        tv_tip.setText("Tip "+jObj.get("tip"));
        tv_totalItem.setText("Total Items: "+itemAmount);
        tv_total.setText("Total "+ jObj.get("totalPrice"));
        ll.addView(tv_tax);
        ll.addView(tv_tip);
        ll.addView(tv_totalItem);
        ll.addView(tv_total);

    }



}