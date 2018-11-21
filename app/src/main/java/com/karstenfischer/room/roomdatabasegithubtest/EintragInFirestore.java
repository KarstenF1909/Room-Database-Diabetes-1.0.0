package com.karstenfischer.room.roomdatabasegithubtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EintragInFirestore extends AppCompatActivity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Wichtig zum Reden!!!
        TTS.init(getApplicationContext());

        Intent intent = getIntent();
        int blutzucker = intent.getIntExtra("Blutzucker", 0);
        TTS.speak("blut zwei" + blutzucker);
        float be = intent.getFloatExtra("Broteinheiten", 0);
        float bolus = intent.getFloatExtra("Bolus", 0);
        float korrektur = intent.getFloatExtra("Korrektur", 0);
        float basal = intent.getFloatExtra("Basal", 0);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("Blutzucker", blutzucker);
        user.put("Broteinheiten", be);
        user.put("Bolus", bolus);
        user.put("Korrektur", korrektur);
        user.put("Basal", basal);



         SimpleDateFormat simpleDateFormatDatum = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
         SimpleDateFormat simpleDateFormatUhrzeit = new SimpleDateFormat("HH:mm:ss", Locale.GERMAN);
        String datum = simpleDateFormatDatum.format(new Date());
        String uhrzeit = simpleDateFormatUhrzeit.format(new Date());
        String eintragID=datum+"_"+uhrzeit;



        firestore.collection("User").document(eintragID).collection("Einträge")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String TAG = "TAG";
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String TAG = "TAG";
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        finish();

    }
}
