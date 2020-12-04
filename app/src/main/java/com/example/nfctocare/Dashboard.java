package com.example.nfctocare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Dashboard extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indice);


    }


    /** Called when the user taps the Send button */
    public void call(View view) {
        Intent intent = new Intent(this, Call.class);
        startActivity(intent);
    }

    public void url(View view) {
        Intent intent = new Intent(this, Url.class);
        startActivity(intent);
    }
    public void inf_text(View view) {
        Intent intent = new Intent(this, Info_text.class);
        startActivity(intent);
    }


    public void video(View view) {
        Intent intent = new Intent(this, Video.class);
        startActivity(intent);
    }
}