package com.erenyamic.bilpara;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class contact extends AppCompatActivity {

    String email,mesaj,telefon;
    EditText editText,editText2;
    variables variables;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        editText=findViewById(R.id.editTextTextEmailAddress);
        editText2=findViewById(R.id.editTextTextPersonName);
        variables=new variables();
        variables.sharedPreferences=contact.this.getSharedPreferences("com.erenyamic.bilpara", Context.MODE_PRIVATE);
        telefon=variables.sharedPreferences.getString("phone","");
    }
    public void back_home3(View view){
        Intent intent=new Intent(contact.this,menu.class);
        startActivity(intent);
    }
    public boolean isEmailValid(String email){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public void email(View view){
        if (!editText.getText().toString().matches("")&&!editText2.getText().toString().matches("")){
            /*if (!isEmailValid(editText.getText().toString())){
                Toast.makeText(contact.this,"Geçersiz E-Mail",Toast.LENGTH_LONG).show();
            }else{*/
                email=editText.getText().toString();
                mesaj=editText2.getText().toString();
                String url="https://bilpara.net/mail.php";
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.matches("Successfully"))
                            Toast.makeText(contact.this,"Mesajınız İletildi",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(contact.this,"Bir Hata Oluştu",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(contact.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Date date=new Date();
                        Map<String,String> params=new HashMap<>();
                        params.put("email",email);
                        params.put("message",mesaj);
                        params.put("phone",telefon);
                        params.put("date",date.toString());
                        return params;
                    }
                };requestQueue.add(stringRequest);
            //}
        }else
            Toast.makeText(contact.this,"Boş Bırakılamaz",Toast.LENGTH_LONG).show();
    }
}