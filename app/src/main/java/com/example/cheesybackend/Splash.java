package com.example.cheesybackend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    private ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        load = findViewById(R.id.progressbar);

        LoadingScreen s = new LoadingScreen();
        s.execute(1);



    }

    class LoadingScreen extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            load.setProgress(0);
        }

        @Override
        protected Integer doInBackground(Integer... start) {
            int a=0;
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e){
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