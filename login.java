package com.erenyamic.bilpara;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
import java.util.Random;

public class login extends AppCompatActivity {

    TextView textView1,textView2,textView3,textView4;
    Button btn1,btn2,btn3;
    EditText phone,verify;
    String number,username;
    CountDownTimer timer;
    variables variables;
    RadioButton radioButton,radioButton2;
    int code,n1=60,n2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Ata();
    }
    public void Ata(){
        phone=findViewById(R.id.editTextPhone);
        verify=findViewById(R.id.editTextPhone2);
        textView1=findViewById(R.id.textView2);
        btn1=findViewById(R.id.button);
        btn2=findViewById(R.id.button3);
        btn3=findViewById(R.id.button4);
        textView3=findViewById(R.id.textView4);
        textView4=findViewById(R.id.textView6);
        textView2=findViewById(R.id.textView3);
        radioButton2=findViewById(R.id.radioButton2);
        radioButton=findViewById(R.id.radioButton);

        verify.setVisibility(View.GONE);
        btn2.setVisibility(View.GONE);
        btn3.setVisibility(View.GONE);
        textView4.setVisibility(View.GONE);
        variables=new variables();
        variables.sharedPreferences=login.this.getSharedPreferences("com.erenyamic.bilpara", Context.MODE_PRIVATE);

    }

    public void sendCode(View view){
        number=phone.getText().toString();


        if (!number.matches("")&&number.length()==10&&radioButton.isChecked()&&radioButton2.isChecked()){


           Random rnd2=new Random();int s1=rnd2.nextInt(8999)+1000;username="bilpara"+s1;



           verify.setVisibility(View.VISIBLE);
           btn2.setVisibility(View.VISIBLE);
           btn1.setVisibility(View.GONE);
           phone.setVisibility(View.GONE);
           textView3.setVisibility(View.GONE);
           btn3.setVisibility(View.VISIBLE);
           btn3.setEnabled(false);
           textView4.setVisibility(View.VISIBLE);
           textView1.setText("Doğrulama Kodu");
           textView2.setText("Kodun gönderildiği numara +90 "+number);

           Random rnd=new Random();
           code = rnd.nextInt(8999)+1000;
            String url="https://bilpara.net/login.php";
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(login.this,"Kod Gönderildi",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(login.this,"Giriş Başarısız",Toast.LENGTH_LONG).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String > map=new HashMap<>();
                    map.put("phone",number);
                    map.put("code",String.valueOf(code));
                    return  map;
                }
            };requestQueue.add(stringRequest);


           timer=new CountDownTimer(60000,1000) {
               @Override
               public void onTick(long l) {
                    n1--;
                    textView4.setText("Kodun geçerlilik süresi: "+n1);
               }

               @Override
               public void onFinish() {
                    btn3.setEnabled(true);
                   n1=60;
                   code = rnd.nextInt(8999)+1000;
               }
           }.start();
       }else{
            if (!radioButton.isChecked()||!radioButton2.isChecked())
                Toast.makeText(login.this,"Kullanım Şartlarını ve Gizlilik Politikasını kabul etmelisiniz",Toast.LENGTH_LONG).show();
            else if(number.length()!=10)
                Toast.makeText(login.this,"Geçerli Bir Numara Giriniz",Toast.LENGTH_LONG).show();

            else
                Toast.makeText(login.this,"Numarayı Kontrol Ediniz",Toast.LENGTH_LONG).show();
        }

    }
    public void Verification(View view){
        String inputCode=verify.getText().toString();
        if (inputCode.equals(String.valueOf(code))){
            String url="https://bilpara.net/verification.php";
            RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.matches("Başarılı")){
                        variables.sharedPreferences.edit().putString("phone",number).apply();
                        n2=1;variables.sharedPreferences.edit().putInt("n",n2).apply();variables.sharedPreferences.edit().putString("username",username).apply();
                        Intent intent=new Intent(login.this, MainActivity2.class);
                        startActivity(intent);
                    }else if(response.matches("zaten var")){
                        variables.sharedPreferences.edit().putString("phone",number).apply();
                        n2=1;variables.sharedPreferences.edit().putInt("n",n2).apply();
                        Intent intent=new Intent(login.this, MainActivity2.class);
                        startActivity(intent);
                    }else{
                        n2=0;variables.sharedPreferences.edit().putInt("n",n2).apply();
                        Toast.makeText(login.this,response,Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(login.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("phone",number);
                    params.put("username",username);
                    return params;
                }
            };requestQueue.add(stringRequest);
        }else
            Toast.makeText(login.this,"Geçersiz Doğrulama",Toast.LENGTH_LONG).show();
    }

    public void sendAgain(View view){
        number=phone.getText().toString();
        if (!number.matches("")&&number.length()==10){
            verify.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn1.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
            btn3.setVisibility(View.VISIBLE);
            btn3.setEnabled(false);
            textView4.setVisibility(View.VISIBLE);
            textView1.setText("Doğrulama Kodu");
            textView2.setText("Kodun gönderildiği numara +90 "+number);

            Random rnd=new Random();
            code = rnd.nextInt(8999)+1000;
            String url="https://bilpara.net/login.php";
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(login.this,"Kod Gönderildi",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(login.this,"Giriş Başarısız",Toast.LENGTH_LONG).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String > map=new HashMap<>();
                    map.put("phone",number);
                    map.put("code",String.valueOf(code));
                    return  map;
                }
            };requestQueue.add(stringRequest);


            timer=new CountDownTimer(60000,1000) {
                @Override
                public void onTick(long l) {
                    n1--;
                    textView4.setText("Kodun geçerlilik süresi: "+n1);
                }

                @Override
                public void onFinish() {
                    btn3.setEnabled(true);
                    n1=60;
                    code = rnd.nextInt(8999)+1000;
                }
            }.start();
        }else
            Toast.makeText(login.this,"Numarayı Kontrol Ediniz",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {

        if (variables.pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Çıkmak için tekrar basınız", Toast.LENGTH_SHORT).show();
        }
        variables.pressedTime = System.currentTimeMillis();
    }
}