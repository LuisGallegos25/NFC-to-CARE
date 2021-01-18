package com.example.nfctocare;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import OpenHelper.SQLite_OpenHelper;

public class CallList extends AppCompatActivity {

    ListView list_call;
    ArrayList<String> list;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        CargarList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        list_call = (ListView)findViewById(R.id.list);
        CargarList();
        list_call.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(CallList.this, list.get(position),Toast.LENGTH_SHORT).show();
                int clave = Integer.parseInt(list.get(position).split(" ")[0]);
                String nombres = list.get(position).split(" ")[1];
                String numero = list.get(position).split(" ")[2];
                Intent intent = new Intent(CallList.this, CallUpdate.class);
                intent.putExtra("id", clave);
                intent.putExtra("nombres", nombres);
                intent.putExtra("numero", numero);
                startActivity(intent);
            }
        });

        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> ListCall(){
        ArrayList<String> datos = new ArrayList<>();
        SQLite_OpenHelper helper = new SQLite_OpenHelper(this,"nfc",null,1);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        String sql = "Select id, nombres, numero From call";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

        if (cursor.moveToFirst()){
            do {
                String linea = cursor.getInt(0) + " "+ cursor.getString(1) + " " + cursor.getString(2);
                datos.add(linea);
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return datos;
    }

    private void CargarList(){
        list = ListCall();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        list_call.setAdapter(adapter);
    }

}
