package com.purba.fapstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.purba.fapstore.R;

public class MainActivity2 extends AppCompatActivity {
    private static int timeout=5000;
    TextView txt;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        checkconnection();
        txt=findViewById(R.id.tex);
        img=findViewById(R.id.img);

        Animation animation= AnimationUtils.loadAnimation(MainActivity2.this,R.anim.myanim);
        img.startAnimation(animation);
        txt.startAnimation(animation);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        },timeout);

    }
    public void checkconnection() {
        ConnectivityManager mg=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=mg.getActiveNetworkInfo();
        if(null!=activeNetwork){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                Toast.makeText(this,"Wifi Enabled",Toast.LENGTH_SHORT).show();
            }
            else  if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(this,"Mobile Data Enabled",Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();

        }
    }



}