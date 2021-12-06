package com.example.cheesybackend;

import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.END;
import static android.view.Gravity.FILL_HORIZONTAL;
import static android.view.Gravity.START;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Optional;

public class Checkout extends AppCompatActivity {


    LinearLayout.LayoutParams etParas, etParam2;
    JSONObject jObj;
    int itemAmount;
    ArrayList<TextView> views;
    LinearLayout ll, ll2;
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
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    private PaymentsClient paymentsClient;
    private static final long SHIPPING_COST_CENTS = 90 * PaymentsUtil.CENTS_IN_A_UNIT.longValue();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ll=findViewById(R.id.myMainLayout);
//        ll2=findViewById(R.id.secondLayout);
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
        etParas=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.HORIZONTAL

        );

        etParam2=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
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

//            ll2 = new LinearLayout(this);
//            ll2.setLayoutParams(etParam2);
            tv_itemName= new TextView(this);
            tv_itemPrice = new TextView(this);
            tv_itemName.setTextSize(20);
            tv_itemPrice.setTextSize(20);
            tv_itemPrice.setGravity(START);
            tv_itemName.setText(jObj.getString("item"+String.valueOf(i)));
            tv_itemPrice.setText(jObj.getString(String.valueOf(i)));
            tv_itemPrice.setGravity(END);
            views.add(tv_itemName);
            views.add(tv_itemPrice);
            ll2.addView(tv_itemName);
            ll2.addView(tv_itemPrice);

        }

//        tv_tax = new TextView(this);
//        tv_tip = new TextView(this);
//        tv_total = new TextView(this);
//        tv_totalItem = new TextView(this);
//        tv_total = new TextView(this);
//        tv_totalItem.setText("Total Items: "+itemAmount);
//        tv_tax.setTop(25);
//        tv_tax.setGravity(END);
//        tv_tip.setGravity(END);
//        tv_total.setGravity(END);
//        tv_tax.setText("Tax "+String.format("%.2f", jObj.get("tax")));
//        tv_tip.setText("Tip "+String.format("%.2f", jObj.get("tip")));
//        tv_total.setText("Total "+ String.format("%.2f",jObj.get("totalPrice")));
//        ll.addView(tv_totalItem);
//        ll.addView(tv_tax);
//        ll.addView(tv_tip);
//        ll.addView(tv_total);
//        isReadyToPay();

    }

    private void isReadyToPay() {
        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        IsReadyToPayRequest readyToPayRequest = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());

        Task<Boolean> task = paymentsClient.isReadyToPay(readyToPayRequest);
        task.addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                Log.d("key", "show button");
                showGooglePlayButton(task.isComplete());
                Log.d("KEY", "ready to pay");
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
        Log.d("key", "in show button");
        if(userIsReadyToPay){
            Log.d("key", "user is ready");
            pay = new Button(this);
            pay.setBackground(getDrawable(R.drawable.buy_with_googlepay_button_content));
//            ll.addView(pay);
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
            Log.d("REQUEST",request.toJson());

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

//
//    private static JSONObject baseConfigurationJson() throws JSONException {
//        return new JSONObject()
//                .put("apiVersion", 2)
//                .put("apiVersionMinor", 0)
//                .put("allowedPaymentMethods", new JSONArray().put(getCardPaymentMethod()));
//    }

//                try {
//                    paymentRequestJson
//                            .put("totalPrice", jObj.get("totalPrice"))
//                            .put("totalPriceStatus", "FINAL")
//                            .put("currencyCode", "USD");
//                    paymentRequestJson
//                            .put("merchantInfo", new JSONObject()
//                                    .put("merchantId", "01234567890123456789")
//                                    .put("merchantName",jObj.getString("name")));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }