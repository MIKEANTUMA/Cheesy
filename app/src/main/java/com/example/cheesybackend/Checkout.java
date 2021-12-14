package com.example.cheesybackend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public class Checkout extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference currentUser;

    RelativeLayout relativeLayout;
    RelativeLayout.LayoutParams layoutParams;
    LinearLayout.LayoutParams etParas;
    LinearLayout ll;
    JSONObject jObj;
    int itemAmount;
    ArrayList<TextView> views;
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
    Button pay;
    private ArrayList<Items> it;

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    private PaymentsClient paymentsClient;
    private static final long SHIPPING_COST_CENTS = 90 * PaymentsUtil.CENTS_IN_A_UNIT.longValue();


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

        Wallet.WalletOptions walletOptions =new Wallet.WalletOptions.Builder()
                .setEnvironment(Constants.PAYMENTS_ENVIRONMENT)
                .setTheme(WalletConstants.THEME_DARK)
                .build();


        paymentsClient = Wallet.getPaymentsClient(this,walletOptions);

        itemAmount = intent.getIntExtra("itemAmount", 0);

        try {
            jObj = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        etParas = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        try {
            populateReceipt();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAuth = FirebaseAuth.getInstance();
    }

    //Dynamically creates the receipt
    public void populateReceipt() throws JSONException {
        name.setText( jObj.getString("name"));
        phoneNumber.setText( jObj.getString("phoneNumber"));
        website.setText( jObj.getString("website"));
        dateTime.setText(jObj.getString("dateTime"));

        it = new ArrayList<>();
        for(int i = 0; i<itemAmount;i++){

            //initializes relative layout
            relativeLayout = new RelativeLayout(this);
            relativeLayout.setLayoutParams(layoutParams);
            layoutParams.setMargins(0,15,0,5);

            //initializes of textView for items and price
            tv_itemName= new TextView(this);
            tv_itemPrice = new TextView(this);
            tv_itemName.setTextSize(20);
            tv_itemPrice.setTextSize(20);

            //setting params for itemName and itemPrice
            //Width = Match & Height = Wrap
            tv_itemName.setLayoutParams(layoutParams);
            tv_itemPrice.setLayoutParams(layoutParams);

            //setting the text of itemName & itemPrice
            tv_itemName.setText(jObj.getString("item"+String.valueOf(i)));
            tv_itemPrice.setText(String.format("%40s", jObj.getString(String.valueOf(i))));
            it.add(new Items(tv_itemName.getText().toString(), tv_itemPrice.getText().toString().trim()));

            //setting id for itemName for layout alignment
            tv_itemName.setId(R.id.hybrid);


            //layout param to align textViews
            layoutParams.addRule(RelativeLayout.RIGHT_OF, tv_itemName.getId());

            //adding view to arrayList
            views.add(tv_itemName);
            views.add(tv_itemPrice);

            //adding textView to relativeLayout
            relativeLayout.addView(tv_itemName);
            relativeLayout.addView(tv_itemPrice, layoutParams);

            //adding relativeLayout to ll
            ll.addView(relativeLayout);
        }
        //initializes tax, tip, total, totalItems
        tv_tax = new TextView(this);
        tv_tip = new TextView(this);
        tv_total = new TextView(this);
        tv_totalItem = new TextView(this);
//        tv_total = new TextView(this);

        //setting margin for etParas
        etParas.setMargins(0,100,0,10);


        //setting textSize
        tv_totalItem.setTextSize(20);
        tv_tax.setTextSize(20);
        tv_tip.setTextSize(20);
        tv_total.setTextSize(20);

        tv_totalItem.setText("Total Items:\t" + itemAmount);
        tv_tax.setText("Tax: " + String.format("%.2f", jObj.get("tax")));
        tv_tip.setText("Tip: " + String.format("%.2f", jObj.get("tip")));
        tv_total.setText("Total: " + String.format("%.2f", jObj.get("totalPrice")));


        //setting params to set margin
        tv_totalItem.setLayoutParams(etParas);

        //setting gravity to align to the left of screen
        tv_tax.setGravity(Gravity.END);
        tv_tip.setGravity(Gravity.END);
        tv_total.setGravity(Gravity.END);

        ll.addView(tv_totalItem);
        ll.addView(tv_tax);
        ll.addView(tv_tip);
        ll.addView(tv_total);
        isReadyToPay();

    }

    private void isReadyToPay() {
        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        IsReadyToPayRequest readyToPayRequest = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());

        Task<Boolean> task = paymentsClient.isReadyToPay(readyToPayRequest);
        task.addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                showGooglePlayButton(task.isComplete());
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("KEYERRRRROOOOOOOR", e.toString());
            }
        });
    }

    private void showGooglePlayButton(boolean userIsReadyToPay){
        if(userIsReadyToPay){

            //initializing google pay button
            pay = new Button(this);

            //initializing FrameLayout
            FrameLayout frameLayout = new FrameLayout(this);

            //FrameLayout params 2x Match_Parent
            FrameLayout.LayoutParams flParams= new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            );

            //setting the FrameLayout parameters
            frameLayout.setLayoutParams(flParams);


            //Creating textView to put behind button
            TextView back = new TextView(this);

            //sets the TextView to black
            back.setBackgroundResource(R.drawable.btn_style_black);

            //sets the pay background
            pay.setBackground(getDrawable(R.drawable.buy_with_googlepay_button_content));

            //second frameLayout to wrap content
            FrameLayout.LayoutParams wrapLayout = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );

            //sets text size and margins & gravity
            back.setTextSize(40);
            wrapLayout.setMargins(10, 20,10,10);
            wrapLayout.gravity = Gravity.BOTTOM;


            //sets back & pay textView layout params
            back.setLayoutParams(wrapLayout);
            pay.setLayoutParams(wrapLayout);

            //adds back & pay to the frameLayout
            frameLayout.addView(back);
            frameLayout.addView(pay);
            ll.addView(frameLayout);

            pay.setOnClickListener(view -> {
                try {
                    loadPaymentData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void loadPaymentData() throws JSONException {

        try{
            double Price = (double) jObj.get("totalPrice");
            long PriceCents = Math.round(Price * PaymentsUtil.CENTS_IN_A_UNIT.longValue());
            long totalpriceCents = PriceCents + SHIPPING_COST_CENTS;

            Optional<JSONObject> paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(totalpriceCents);

            if (!paymentDataRequestJson.isPresent()) {
                return;
            }

            PaymentDataRequest request =
                    PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());


            if (request != null) {
                AutoResolveHelper.resolveTask(
                        paymentsClient.loadPaymentData(request),
                        this, LOAD_PAYMENT_DATA_REQUEST_CODE);
            }

        } catch (Exception e) {
            throw new RuntimeException("The price cannot be deserialized from the JSON object.");
        }

    }

    private void handlePaymentSuccess(PaymentData paymentData) {

        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        final String paymentInfo = paymentData.toJson();
        if (paymentInfo == null) {
            return;
        }

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".

            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
            final String token = tokenizationData.getString("token");
            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    this, billingName,
                    Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token: ", token);

            FirebaseUser user = mAuth.getCurrentUser();
            currentUser = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
            jObj.getString("phoneNumber");
            jObj.getDouble("tax");
            jObj.getDouble("tip");

            jObj.getString("website");

            UserOrder ord = new UserOrder(jObj.getInt("totalItems"),jObj.getString("dateTime"),
                    jObj.getString("name"), Math.round(jObj.getDouble("totalPrice")*100.0)/100.0, it,
                    jObj.getString("website"), jObj.getString("phoneNumber"),
                    Math.round(jObj.getDouble("tax")*100.0)/100.0,Math.round(jObj.getDouble("tip")*100.0)/100.0);


            currentUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User g = dataSnapshot.getValue(User.class);
                    int b = g.getTotalOrders() + 1;

                    currentUser.child("totalOrders").setValue(g.getTotalOrders()+1);
                    currentUser.child("Orders").child("Order"+ b).setValue(ord);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("Firebase Error", "Couldn't grab user");
                }

            });
            startActivity(new Intent(Checkout.this, showRestaurants.class));

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }

    private void handleError(int statusCode) {
        Log.e("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                }

                // Re-enables the Google Pay payment button.
                //googlePayButton.setClickable(true);
        }
    }
}
