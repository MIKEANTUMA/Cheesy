package com.example.cheesybackend;

import static com.example.cheesybackend.PaymentsUtil.getCardPaymentMethod;
import static com.example.cheesybackend.PaymentsUtil.getIsReadyToPayRequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cheesybackend.databinding.GooglepayButtonBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.Wallet.WalletOptions;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
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
    Button pay;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    JSONObject paymentRequestJson = null;
    private PaymentsClient paymentsClient;
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
        isReadyToPay();
        pay.setOnClickListener(view -> {

            try {
                paymentRequestJson = baseConfigurationJson();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                paymentRequestJson
                        .put("totalPrice", jObj.get("totalPrice"))
                        .put("totalPriceStatus", "FINAL")
                        .put("currencyCode", "USD");
                paymentRequestJson
                        .put("merchantInfo", new JSONObject()
                                .put("merchantId", "01234567890123456789")
                                .put("merchantName",jObj.getString("name")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loadPaymentData();

        });
    }

    private void isReadyToPay() throws JSONException {
        IsReadyToPayRequest readyToPayRequest = IsReadyToPayRequest.fromJson(baseConfigurationJson().toString());

        Task<Boolean> task = paymentsClient.isReadyToPay(readyToPayRequest);
        task.addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                showGooglePlayButton(task.isComplete());
                Log.d("KEY", "ready to pay");
            }
        });
    }

    private static JSONObject baseConfigurationJson() throws JSONException {
        return new JSONObject()
                .put("apiVersion", 2)
                .put("apiVersionMinor", 0)
                .put("allowedPaymentMethods", new JSONArray().put(getCardPaymentMethod()));
    }

    private void showGooglePlayButton(boolean userIsReadyToPay){
        if(userIsReadyToPay){
            pay = new Button(this);
            pay.setBackground(getDrawable(R.drawable.buy_with_googlepay_button_content));
        }
        else{

        }
    }

    private void loadPaymentData(){
        final PaymentDataRequest request = PaymentDataRequest.fromJson(paymentRequestJson.toString());
        AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                this, LOAD_PAYMENT_DATA_REQUEST_CODE);
    }

}