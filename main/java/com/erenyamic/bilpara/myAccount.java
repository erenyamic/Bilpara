package com.erenyamic.bilpara;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class myAccount extends AppCompatActivity {

    variables variables;
    String phone;
    TextView textView,textView2,textView3;
    EditText editText;
    Button btn;
    int nm1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Ata();
    }
    public void Ata(){
        variables=new variables();
        variables.sharedPreferences=myAccount.this.getSharedPreferences("com.erenyamic.bilpara", Context.MODE_PRIVATE);
        phone=variables.sharedPreferences.getString("phone","");
        textView=findViewById(R.id.textView38);
        textView2=findViewById(R.id.textView44);
        textView3=findViewById(R.id.textView39);
        editText=findViewById(R.id.editTextTextPersonName4);editText.setVisibility(View.GONE);
        btn=findViewById(R.id.button14);btn.setVisibility(View.GONE);
        getInfo();
    }
    public void back_home3(View view){
        Intent intent=new Intent(myAccount.this,menu.class);
        startActivity(intent);
    }
    public void cikis(View view){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(myAccount.this);
        alertDialog.setTitle("Uyarı");
        alertDialog.setMessage("Çıkmak istediğinizden eminmisiniz ?");
        alertDialog.setNegativeButton("Hayır",null);
        alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                variables.sharedPreferences.edit().putInt("n",0).apply();
                Intent intent=new Intent(myAccount.this,MainActivity.class);
                startActivity(intent);
            }
        });alertDialog.show();
    }
    public void sil(View view){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(myAccount.this);
        alertDialog.setTitle("Uyarı");
        alertDialog.setMessage("Hesabınızı silmek istediğinizden emin misiniz ?");
        alertDialog.setNegativeButton("Hayır",null);
        alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!phone.matches("")){
                    String url="https://bilpara.net/deleteAccount.php";
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.matches("Successfully")){
                                variables.sharedPreferences.edit().putInt("n",0).apply();
                                Intent intent=new Intent(myAccount.this,MainActivity.class);
                                startActivity(intent);
                            }else
                                Toast.makeText(myAccount.this,"Bilinmeyen bir hata oluştu",Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(myAccount.this,error.toString(),Toast.LENGTH_LONG).show();
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
                }else
                    Toast.makeText(myAccount.this,"Hata var",Toast.LENGTH_LONG).show();

            }
        });alertDialog.show();
    }
    public  void getInfo(){
        String url="https://bilpara.net/info.php";
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        try {
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (!response.matches("")){
                        String[] dizi=response.split("---");
                        textView.setText(dizi[0]);
                        textView2.setText(dizi[1]);
                        textView3.setText(dizi[2]);
                    }else
                        Toast.makeText(myAccount.this,"Bir sorun oluştu",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(myAccount.this,error.toString(),Toast.LENGTH_LONG).show();
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
        }catch (Exception e){
            Toast.makeText(myAccount.this,e.getStackTrace().toString(),Toast.LENGTH_LONG).show();
        }
    }
    public void newUsername(View view){
        nm1++;
        if (nm1%2==1){
            editText.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);
        }else if (nm1%2==0){
            editText.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
        }
    }
    public void confirmUsername(View view){
        if (!editText.getText().toString().matches("")){
            String url="https://bilpara.net/newUsername.php";
            RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.matches("Successfully")) {

                        Toast.makeText(myAccount.this,"Kullanıcı adı başarıyla değiştirildi",Toast.LENGTH_LONG).show();
                        getInfo();
                    }
                    else if (response.matches("Kullanıcı adı zaten alınmış")){
                        Toast.makeText(myAccount.this,response,Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(myAccount.this,"Hata oluştu",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(myAccount.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("phone",phone);
                    params.put("newusername",editText.getText().toString());
                    return params;
                }
            };requestQueue.add(stringRequest);
        }else
            Toast.makeText(myAccount.this,"Boş bırakılamaz",Toast.LENGTH_LONG).show();
    }
}