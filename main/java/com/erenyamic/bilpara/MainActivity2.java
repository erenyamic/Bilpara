package com.erenyamic.bilpara;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    ScrollView scrollView;
    String cevap, odul, yarisma,str1;
    TextView soru, kacinci_yarisma, odul_ne,textView2;
    LinearLayout layout;
    LinearLayout.LayoutParams lp;
    variables variables;
    String[] dizi,dizi2;
    TextView textView,textView3;
    EditText editText;
    String phone, username;
    List<TextView> ipucular;
    Button btn;
    int s5 = 0, s7 = 0;
    long zaman=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Ata();
        yayin_tarihi();

    }

    public void Ata() {
        variables = new variables();
        variables.sharedPreferences = MainActivity2.this.getSharedPreferences("com.erenyamic.bilpara", Context.MODE_PRIVATE);
        phone = variables.sharedPreferences.getString("phone", "");
        username = variables.sharedPreferences.getString("username", "");
        scrollView = findViewById(R.id.scrollView2);
        layout=findViewById(R.id.linear2);
        lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        editText = findViewById(R.id.editTextTextPersonName2);
        soru = findViewById(R.id.textView9);textView3=findViewById(R.id.textView12);
        kacinci_yarisma = findViewById(R.id.textView8);textView2=findViewById(R.id.textView10);
        odul_ne = findViewById(R.id.textView7);
        btn = findViewById(R.id.button8);
        ipucular=new ArrayList<>();
        getTips();
        getQuestion();
        cevap_hakki2();
        bilindi_mi();

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

    public void cevapla(View view) {
        if (!editText.getText().toString().matches("")){
            String url="https://bilpara.net/fullcevap2.php";
            RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.matches("Successfully")){
                        Toast.makeText(MainActivity2.this,"Cevap kaydedilemedi",Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity2.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("phone",phone);
                    params.put("cevap",editText.getText().toString());
                    params.put("yarisma",yarisma);
                    return params;
                }
            };requestQueue.add(stringRequest);
        }

        if (editText.getText().toString().matches(cevap) && !editText.getText().toString().matches("")) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity2.this);
            alertDialog.setTitle("Tebrikler");
            alertDialog.setMessage("Bravo doğru bildiniz");
            alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String url7 = "https://bilpara.net/point1.php";
                    RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url7, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.matches("")) {
                                s7 = Integer.parseInt(response);
                                s7++;
                            } else
                                Toast.makeText(MainActivity2.this, "Hata", Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("phone", phone);
                            return params;
                        }
                    };
                    requestQueue1.add(stringRequest1);


                    String url = "https://bilpara.net/answer.php";
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.matches("Successfully")) {
                                String url9 = "https://bilpara.net/point2.php";
                                RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url9, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (!response.matches("Successfully"))
                                            Toast.makeText(MainActivity2.this, "Hata", Toast.LENGTH_LONG).show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }) {
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("phone", phone);
                                        params.put("s7", Integer.toString(s7));
                                        return params;
                                    }
                                };
                                requestQueue2.add(stringRequest2);
                                Toast.makeText(MainActivity2.this, "Cevabınız kaydedildi. Ödülünüz için size haber vereceğiz", Toast.LENGTH_LONG).show();
                                btn.setEnabled(false);

                            } else if (response.matches("zaten var")){
                                Toast.makeText(MainActivity2.this, "Doğru cevap zaten bulundu. Gelecek yarışmalarda şans dileriz.", Toast.LENGTH_LONG).show();
                                btn.setEnabled(false);
                            }

                            else
                                Toast.makeText(MainActivity2.this, "Hata oluştu", Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("phone", phone);
                            params.put("username", username);
                            params.put("answer", editText.getText().toString());
                            params.put("yarisma", yarisma);
                            return params;

                        }
                    };
                    requestQueue.add(stringRequest);

                }
            });
            alertDialog.show();
        } else {
            if (editText.getText().toString().matches(""))
                Toast.makeText(MainActivity2.this, "Boş bırakılamaz", Toast.LENGTH_LONG).show();
            else {
                String url5 = "https://bilpara.net/limit.php";
                RequestQueue requestQueue5 = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest5 = new StringRequest(Request.Method.POST, url5, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.matches("")) {
                            s5 = Integer.parseInt(response);
                            if (s5 > 0){
                                s5--;
                            }

                            else {
                                btn.setEnabled(false);
                                Toast.makeText(MainActivity2.this, "Cevap hakkınız kalmadı", Toast.LENGTH_LONG).show();
                            }
                        } else
                            Toast.makeText(MainActivity2.this, "Hata", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("phone", phone);
                        return params;
                    }
                };
                requestQueue5.add(stringRequest5);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity2.this);
                alertDialog.setTitle("Maalesef bilemediniz");
                alertDialog.setMessage("Gelecek yarışmalarda şans dileriz");
                alertDialog.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            String url = "https://bilpara.net/cevap_hakki.php";
                            RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (!response.matches("Successfully")){
                                        Toast.makeText(MainActivity2.this, "Hata", Toast.LENGTH_LONG).show();
                                    }
                                    cevap_hakki2();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(MainActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("phone", phone);
                                    params.put("s5", Integer.toString(s5));
                                    return params;
                                }
                            };
                            requestQueue1.add(stringRequest1);
                        } catch (Exception e) {
                            Toast.makeText(MainActivity2.this, e.getStackTrace().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                alertDialog.show();
            }

        }
    }

    public void winners(View view) {
        Intent intent = new Intent(MainActivity2.this, winners.class);
        startActivity(intent);
    }

    public void menu(View view) {
        Intent intent = new Intent(MainActivity2.this, menu.class);
        startActivity(intent);
    }

    public void getQuestion() {
        String url = "https://bilpara.net/question.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.matches("")) {
                    String[] dizi2 = response.split("---");
                    soru.setText(dizi2[0]);
                    cevap = dizi2[1];
                    odul = dizi2[2];
                    yarisma = dizi2[3];
                    kacinci_yarisma.setText(yarisma + ". Yarışma");
                    odul_ne.setText("Ödül " + odul + " TL");

                } else
                    Toast.makeText(MainActivity2.this, "Bir hata oluştu", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("yes", "bir");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getTips() {
        String url = "https://bilpara.net/tips.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.matches("")) {
                    dizi = response.split("---");
                    str1=dizi[0];
                    for (int i=0;i<dizi.length;i++){
                        if (i<3){
                            textView=new TextView(MainActivity2.this);
                            textView.setText("İPUCU "+(i+1));
                            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            textView.setTextColor(Color.WHITE);
                            textView.setId(i);
                            textView.setTextSize(20);
                            textView.setTypeface(null, Typeface.BOLD);
                            textView.setWidth(layout.getWidth());
                            textView.setPadding(20,20,20,20);
                            textView.setBackgroundResource(R.drawable.bottomborder);
                            ipucular.add(textView);
                            textView.setLayoutParams(lp);
                            layout.addView(textView);ss();
                        }else
                        {
                            textView=new TextView(MainActivity2.this);
                            textView.setText("EKSTRA İPUCU "+(i-2));
                            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(20);
                            textView.setTypeface(null, Typeface.BOLD);
                            textView.setWidth(layout.getWidth());
                            textView.setPadding(20,20,20,20);
                            textView.setBackgroundResource(R.drawable.bottomborder);
                            ipucular.add(textView);
                            textView.setLayoutParams(lp);
                            layout.addView(textView);ss();
                        }
                    }

                } else
                    Toast.makeText(MainActivity2.this, "Bir hata oluştu", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ok", "ok");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void ss(){
        for (TextView item:ipucular) {
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (item.getText().toString()) {
                            case "İPUCU 1":
                                item.setText(dizi[0]);
                                break;
                            case "İPUCU 2":
                                    item.setText(dizi[1]);
                                break;
                            case "İPUCU 3":
                                    item.setText(dizi[2]);
                                break;
                            case "EKSTRA İPUCU 1":
                                    item.setText(dizi[3]);
                                break;
                            case "EKSTRA İPUCU 2":
                                    item.setText(dizi[4]);
                                break;
                            case "EKSTRA İPUCU 3":
                                    item.setText(dizi[5]);
                                break;
                            case "EKSTRA İPUCU 4":
                                    item.setText(dizi[6]);
                                break;
                            case "EKSTRA İPUCU 5":
                                    item.setText(dizi[7]);
                                break;
                            case "EKSTRA İPUCU 6":
                                    item.setText(dizi[8]);
                                break;
                            case "EKSTRA İPUCU 7":
                                    item.setText(dizi[9]);
                                break;
                            case "EKSTRA İPUCU 8":
                                    item.setText(dizi[10]);
                                break;
                            case "EKSTRA İPUCU 9":
                                    item.setText(dizi[11]);
                                break;
                            case "EKSTRA İPUCU 10":
                                    item.setText(dizi[12]);
                                break;
                            default:
                                if (item.getText().toString().matches(dizi[0])){
                                    item.setText("İPUCU 1");
                                }else if (item.getText().toString().matches(dizi[1])){
                                    item.setText("İPUCU 2");
                                }else if (item.getText().toString().matches(dizi[2])){
                                    item.setText("İPUCU 3");
                                }else if (item.getText().toString().matches(dizi[3])){
                                    item.setText("EKSTRA İPUCU 1");
                                }else if (item.getText().toString().matches(dizi[4])){
                                    item.setText("EKSTRA İPUCU 2");
                                }else if (item.getText().toString().matches(dizi[5])){
                                    item.setText("EKSTRA İPUCU 3");
                                }else if (item.getText().toString().matches(dizi[6])){
                                    item.setText("EKSTRA İPUCU 4");
                                }else if (item.getText().toString().matches(dizi[7])){
                                    item.setText("EKSTRA İPUCU 5");
                                }else if (item.getText().toString().matches(dizi[8])){
                                    item.setText("EKSTRA İPUCU 6");
                                }else if (item.getText().toString().matches(dizi[9])){
                                    item.setText("EKSTRA İPUCU 7");
                                }else if (item.getText().toString().matches(dizi[10])){
                                    item.setText("EKSTRA İPUCU 8");
                                }else if (item.getText().toString().matches(dizi[11])){
                                    item.setText("EKSTRA İPUCU 9");
                                }else if (item.getText().toString().matches(dizi[12])){
                                    item.setText("EKSTRA İPUCU 10");
                                }
                            break;

                        }
                    }
                });

        }


    }

    public void yayin_tarihi(){
        String url="https://bilpara.net/time1.php";
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.matches("")) {

                    dizi2=response.split("---");
                    sayac();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("ok","yes");
                return params;
            }
        };requestQueue.add(stringRequest);
    }
    public  void sayac(){

        try {
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d2=new Date();

            Date d1=format.parse(dizi2[0]);
            long result=d2.getTime()-d1.getTime();
            zaman=86400000-result;

        } catch (Exception e) {
            Toast.makeText(MainActivity2.this,e.toString(),Toast.LENGTH_LONG).show();
        }



        String url="https://bilpara.net/time2.php";
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());

        new CountDownTimer(zaman,1000) {
            @Override
            public void onTick(long l) {

                textView2.setText("YENİ İPUCU: "+(l/3600000)+":"+((l%3600000)/60000)+":"+((((l%3600000)%60000))/1000));
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params=new HashMap<>();
                        params.put("time",Long.toString(l));
                        return params;
                    }
                };requestQueue.add(stringRequest);
            }

            @Override
            public void onFinish() {

                textView2.setText("YENİ İPUCU YAYINDA");
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity2.this);
                builder.setTitle("Yeni İpucu Yayınlandı");
                builder.setMessage("Yeni ipucuyu satın alarak soruyu bilme şansını arttır.");
                builder.setPositiveButton("Tamam",null);
                builder.show();


            }
        }.start();
    }
    public void cevap_hakki2(){
        String url="https://bilpara.net/cevap_hakki2.php";
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.matches("")){
                    textView3.setText(response);
                }
                if (response.matches("0"))
                    btn.setEnabled(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this,error.toString(),Toast.LENGTH_LONG).show();
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
    public void bilindi_mi(){
        String url="https://bilpara.net/checked.php";
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.matches("cevap bulundu")){
                    btn.setEnabled(false);
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity2.this);
                    builder.setTitle("Doğru Cevap Bulundu");
                    builder.setMessage("Yeni yarışma yakında başlayacak");
                    builder.setPositiveButton("Tamam",null);
                    builder.show();
                }

                else
                    btn.setEnabled(true);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("yes","ok");
                return params;
            }
        };requestQueue.add(stringRequest);
    }
}