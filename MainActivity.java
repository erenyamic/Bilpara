package com.erenyamic.bilpara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    int i=0,n=0;
    variables variables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        variables=new variables();
        variables.sharedPreferences=MainActivity.this.getSharedPreferences("com.erenyamic.bilpara", Context.MODE_PRIVATE);
        n=variables.sharedPreferences.getInt("n",0);

        if (n==1){
            Handler handler=new Handler();
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    i++;
                    if (i==2){
                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    }
                    handler.postDelayed(this,1150);
                }

            };handler.post(runnable);
        }else{
            Handler handler=new Handler();
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    i++;
                    if (i==2){
                        Intent intent = new Intent(MainActivity.this, login.class);
                        startActivity(intent);
                        finish();
                    }
                    handler.postDelayed(this,1150);
                }

            };handler.post(runnable);
        }
        }



}