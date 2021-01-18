package com.example.nfctocare;
/*
 Degree project for the career of computer engineering
  * NFC application for the care of the elderly "NFC to CARE"
  * Workshop III
  * Engineer Professor Ronald Yeber Cruz Delgado
  * GitHub repository
 *(hhttps://github.com/LuisGallegos25/NFC-to-CARE)
 * 18/11/2020
 */
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import OpenHelper.SQLite_OpenHelper;


public class CallUpdate extends AppCompatActivity {

    private View v;
    private NdefMessage message = null;
    private ProgressDialog dialog;
    Tag currentTag;
    private NFCManager nfcMger;
    EditText txtNombres, txtNumero;
    int id;
    String nombres, numero;


    SQLite_OpenHelper helper=new SQLite_OpenHelper(this,"nfc",null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            id = b.getInt("id");
            nombres = b.getString("nombres");
            numero = b.getString("numero");
        }

        txtNombres = (EditText)findViewById(R.id.nombre);
        txtNumero = (EditText)findViewById(R.id.content);

        txtNombres.setText(nombres);
        txtNumero.setText(numero);

        nfcMger = new NFCManager(this);
        v = findViewById(R.id.mainLyt);

        final EditText et = (EditText) findViewById(R.id.content);

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateCall(id, txtNombres.getText().toString(), txtNumero.getText().toString());
                Toast.makeText(getApplicationContext(),"Actualizacion exitosa",Toast.LENGTH_LONG).show();


                String content = et.getText().toString();

                message =  nfcMger.createUriMessage(content, "tel:");

                if (message != null) {
                    dialog = new ProgressDialog(CallUpdate.this);
                    dialog.setMessage("Acerque la etiqueta por favor");
                    dialog.show();

                }


            }
        });

        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        FloatingActionButton btn_delete = (FloatingActionButton) findViewById(R.id.delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCall(id);
                onBackPressed();

            }
        });


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateCall(int id, String nombres, String numero){

        SQLite_OpenHelper helper = new SQLite_OpenHelper(this,"nfc",null,1);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        String sql = "update call set nombres= '"+ nombres +"', numero='" + numero + "' where id=" +id;
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();

    }

    private void deleteCall(int id){

        SQLite_OpenHelper helper = new SQLite_OpenHelper(this,"nfc",null,1);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        String sql = "delete from call where id=" +id;
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            nfcMger.verifyNFC();
            //nfcMger.enableDispatch();

            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
            IntentFilter[] intentFiltersArray = new IntentFilter[] {};
            String[][] techList = new String[][] { { android.nfc.tech.Ndef.class.getName() }, { android.nfc.tech.NdefFormatable.class.getName() } };
            NfcAdapter nfcAdpt = NfcAdapter.getDefaultAdapter(this);
            nfcAdpt.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
        }
        catch(NFCManager.NFCNotSupported nfcnsup) {
            Snackbar.make(v, "NFC no soportado", Snackbar.LENGTH_LONG).show();
        }
        catch(NFCManager.NFCNotEnabled nfcnEn) {
            Snackbar.make(v, "NFC no activado", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        nfcMger.disableDispatch();
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d("Nfc", "Nuevo intento");
        // It is the time to write the tag
        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (message != null) {
            nfcMger.writeTag(currentTag, message);
            dialog.dismiss();
            Snackbar.make(v, "Etiqueta escrita", Snackbar.LENGTH_LONG).show();

        }
        else {
            // Handle intent

        }
    }



}
