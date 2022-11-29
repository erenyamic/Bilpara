package com.erenyamic.bilpara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class asked_questions extends AppCompatActivity {

    TextView textView,textView1,textView2,textView3,textView4,textView5;
    int s1=0,s2=0,s3=0,s4=0,s5=0,s6=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asked_questions);
        Ata();
    }
    public void backk(View view){
        Intent intent=new Intent(asked_questions.this,menu.class);
        startActivity(intent);
    }
    public void Ata(){
        textView=findViewById(R.id.textView14);
        textView1=findViewById(R.id.textView15);
        textView2=findViewById(R.id.textView16);
        textView3=findViewById(R.id.textView17);
        textView4=findViewById(R.id.textView18);
        textView5=findViewById(R.id.textView19);
    }
    public void soru1(View view){
        s1++;
        if (s1%2==1){
            textView.setText("Bilpara nedir ?\n---\nBilpara bilgi yarışması uygulamasıdır.");
        }else
            textView.setText("Bilpara nedir ?");
    }
    public void soru2(View view){
        s2++;
        if (s2%2==1){
            textView1.setText("Ücretli midir ?\n---\nHayır ücretsiz bir şekilde indirilip oynanabilir.");
        }else
            textView1.setText("Ücretli midir ?");
    }
    public void soru3(View view){
        s3++;
        if (s3%2==1){
            textView2.setText("Para ödülü gerçek midir ?\n---\nEvet gerçek para ödülüdür");
        }else
            textView2.setText("Para ödülü gerçek midir ?");
    }
    public void soru4(View view){
        s4++;
        if (s4%2==1){
            textView3.setText("Ödülü nasıl veriyorsunuz ?\n---\nKazanan yarışmacıya uygulamada kayıtlı olan cep telefonu numarası ile irtibata geçip EFT/Havale yoluyla banka hesabına gönderilir.");
        }else
            textView3.setText("Ödülü nasıl veriyorsunuz ?");
    }
    public void soru5(View view){
        s5++;
        if (s5%2==1){
            textView4.setText("Neden telefon numarası ?\n---\nHesabın güvenliği ve hesabın senin adına onaylanması için alıyoruz.");
        }else
            textView4.setText("Neden telefon numarası ?");
    }
    public void soru6(View view){
        s6++;
        if (s6%2==1){
            textView5.setText("Telefon numaramı değiştirebilir miyim ?\n---\nTelefon numarasını değişmek mümkün değildir. Hesabınız numaraya özeldir.");
        }else
            textView5.setText("Telefon numaramı değiştirebilir miyim ?");
    }
}