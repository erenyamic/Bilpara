package com.erenyamic.bilpara;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class winners extends AppCompatActivity {
    String phone;
    variables variables;
    LinearLayout layout;
    LinearLayout.LayoutParams lp;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners);
        Ata();
    }
    public void back_home(View view){
        Intent intent = new Intent(winners.this,MainActivity2.class);
        startActivity(intent);
    }
    public void Ata(){
        variables=new variables();
        variables.sharedPreferences=winners.this.getSharedPreferences("com.erenyamic.bilpara", Context.MODE_PRIVATE);
        phone=variables.sharedPreferences.getString("phone","");
        layout=findViewById(R.id.linear);
        lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        String url="https://bilpara.net/winners.php";
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.matches("")){
                    String[] dizi=response.split("---");
                    for (int i=0;i<dizi.length;i++){
                        textView=new TextView(winners.this);
                        textView.setText("#"+(i+1)+"  |  "+dizi[i]);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textView.setTextColor(Color.WHITE);
                        textView.setTextSize(20);
                        textView.setTypeface(null,Typeface.BOLD);
                        textView.setWidth(layout.getWidth());
                        textView.setPadding(10,10,10,10);
                        textView.setBackgroundResource(R.drawable.bottomborder);
                        textView.setLayoutParams(lp);
                        layout.addView(textView);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(winners.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("phone",phone);
                return params;
            }
        };requestQueue.add(stringRequest);
    }
}

