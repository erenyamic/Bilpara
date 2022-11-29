package com.erenyamic.bilpara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void back_home2(View view){
        Intent intent = new Intent(menu.this,MainActivity2.class);
        startActivity(intent);
    }
    public void contact(View view){
        Intent intent=new Intent(menu.this,contact.class);
        startActivity(intent);
    }
    public void MyAccount(View view){
        Intent intent=new Intent(menu.this,myAccount.class);
        startActivity(intent);
    }
    public void answers(View view){
        Intent intent=new Intent(menu.this,answers.class);
        startActivity(intent);
    }
    public void gecmis_yarismalar(View view){
        Intent intent=new Intent(menu.this,past.class);
        startActivity(intent);
    }
    public void sorulan_sorular(View view){
        Intent intent=new Intent(menu.this,asked_questions.class);
        startActivity(intent);
    }
}