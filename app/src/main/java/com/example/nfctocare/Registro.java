package com.example.nfctocare;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import OpenHelper.SQLite_OpenHelper;

public class Registro extends AppCompatActivity {

    Button btnRegistro;
    EditText txtNombres, txtUsuario, txtPassword;

    SQLite_OpenHelper helper=new SQLite_OpenHelper(this,"nfc",null,1);

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        btnRegistro = (Button)findViewById(R.id.btn_reg);
        txtNombres = (EditText)findViewById(R.id.nombres);
        txtUsuario = (EditText)findViewById(R.id.user);
        txtPassword = (EditText)findViewById(R.id.pass);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.abrir();
                helper.insertarRegistro(String.valueOf(txtNombres.getText()), String.valueOf(txtUsuario.getText()),String.valueOf(txtPassword.getText()));
                helper.cerrar();
                Toast.makeText(getApplicationContext(),"Registro exitoso",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });
    }
}
