package com.example.nfctocare;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import OpenHelper.SQLite_OpenHelper;

public class Login extends AppCompatActivity {

    Button btn_registrese;
    Button btn_ingresar;

    SQLite_OpenHelper helper = new SQLite_OpenHelper(this, "nfc",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btn_registrese = (Button)findViewById(R.id.btn_registro);
        btn_registrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),Registro.class);
                startActivity(i);

            }
        });

        btn_ingresar = (Button) findViewById(R.id.btn_ingresar);
        btn_ingresar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText txtUsuario= (EditText)findViewById(R.id.usuario);
                EditText txtPassword = (EditText)findViewById(R.id.password);

                try {
                        Cursor cursor = helper.ConsultarUsuPass
                                (txtUsuario.getText().toString(),txtPassword.getText().toString());
                        if (cursor.getCount()>0){
                            Intent i = new Intent(getApplicationContext(),Dashboard.class);
                            startActivity(i);
                        }  else{
                                Toast.makeText(getApplicationContext(),"Usuario y/o password incorrectos", Toast.LENGTH_LONG).show();
                                 }
                        txtUsuario.setText("");
                        txtPassword.setText("");
                        txtUsuario.findFocus();
            } catch(SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
