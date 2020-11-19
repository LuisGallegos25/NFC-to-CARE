package com.example.nfctocare;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.design.widget.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class NFCManager {

    private Activity activiy;
    private NfcAdapter nfcAdpt;

    public NFCManager(Activity activity) {

        this.activity = activity;
    }

    public void verifyNFC() throws NFCNotSupported, NFCNotEnabled {

        nfcAdpt = NfcAdapter.getDefaultAdapter(activity);

        if (nfcAdpt == null)
            throw new NFCNotSupported();

        if (!nfcAdpt.isEnabled())
            throw new NFCNotEnabled();

    }

    public void enableDispatch() {
        Intent nfcIntent = new Intent(activity, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, nfcIntent, 0);
        IntentFilter[] intentFiltersArray = new IntentFilter[] {};
        String[][] techList = new String[][] { { android.nfc.tech.Ndef.class.getName() }, { android.nfc.tech.NdefFormatable.class.getName() } };


        nfcAdpt.enableForegroundDispatch(activity, pendingIntent, intentFiltersArray, techList);
    }

    public void disableDispatch() {

        nfcAdpt.disableForegroundDispatch(activity);
    }
}
