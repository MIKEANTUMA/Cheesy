package com.example.cheesybackend;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class Splash extends AppCompatActivity {

    private ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        load = findViewById(R.id.progressbar);

        GrabRestaurantData s = new GrabRestaurantData();
        s.execute(1);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

//        ArrayList<String> pizzaNames = new ArrayList<>();
//        pizzaNames.add("america's Incredible Pizza Company");
//        pizzaNames.add("arni's Restaurant");
//        pizzaNames.add("domino's Pizza");
//        pizzaNames.add("grotto Pizza");
//        pizzaNames.add("happy's Pizza");
//        pizzaNames.add("hunt Brothers Pizza");
//        pizzaNames.add("little Caesars");
//        pizzaNames.add("marco's Pizza");


//        for (int i = 0; i < 8; i++) {
//
//            Restaurant restaurant = new Restaurant();
//            restaurant.setName(" II Bacetto");
//            restaurant.setLocation("3982 Jerusalem Ave, Seaford");
//            restaurant.setRating((float) (Math.random() * (5 - 1 + 1) + 1));
//            restaurant.setPhoneNumber("5167281827");
//            restaurant.setWebsite("google.com");
//            restaurant.setDescription("The pizza shop is a place where many people like to go to have dinner with their families. Aside from the delicious food, the waiters or waitresses in the pizza restaurant can significantly add to or subtract from the experience.");
//            GeoPoint geoPoint = new GeoPoint(40.694070217136904, -73.48617198087341);
//            restaurant.setGeoPoint(geoPoint);
//
//            String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(restaurant.getGeoPoint().getLatitude(), restaurant.getGeoPoint().getLongitude()));
//
//            restaurant.setGeohash(hash);
//            Drink d = new Drink("water", 1.99, "bottle of water");
//            Drink d1 = new Drink("pespi", 2.99, "bottle of pepsi");
//            Entree e = new Entree("cheese pizza", "8 slices of cheese pizza", 10.99);
//            Entree e1 = new Entree("Chicken Bacon Ranch pizza", "8 slices", 14.99);
//            Appetizer a = new Appetizer("chicken roll", "6 inch roll", 6.99);
//            Appetizer a1 = new Appetizer("Mozz sticks", "6 mozz sticks", 5.99);
//            ArrayList<Entree> elist = new ArrayList<>();
//            ArrayList<Drink> dlist = new ArrayList<>();
//            ArrayList<Appetizer> alist = new ArrayList<>();
//            elist.add(e);
//            elist.add(e1);
//            dlist.add(d);
//            dlist.add(d1);
//            alist.add(a);
//            alist.add(a1);
//            Menu m = new Menu(elist, dlist, alist);
//            restaurant.setMenu(m);
//
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("restaurants").document(restaurant.getName())
//                    .set(restaurant)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d("key", "DocumentSnapshot successfully written!");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w("key", "Error writing document", e);
//                        }
//                    });
//
//        }
    }
        class GrabRestaurantData extends AsyncTask<Integer, Integer, Integer> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                load.setProgress(0);
            }


            //reset to 5000 before final submission
            @Override
            protected Integer doInBackground(Integer... start) {
                int a = 0;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return a;
            }


            @Override
            protected void onProgressUpdate(Integer... values) {
                load.setProgress(100);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                startActivity(new Intent(Splash.this, Login.class));

            }
        }
    }
