package com.example.nfctocare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indice);


    }
    /** Called when the user taps the Send button */
    public void volver(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}