package com.karstenfischer.room.roomdatabasegithubtest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddEditNoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_ID = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_DECRIPTION";
    public static final String EXTRA_PRIORITY = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_PRIORITY";

    public static final String EXTRA_BLUTZUCKER = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_BLUTZUCKER";
    public static final String EXTRA_BE = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_BE";
    public static final String EXTRA_BOLUS = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_BOLUS";
    public static final String EXTRA_KORREKTUR = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_KORREKTUR";
    public static final String EXTRA_BASAL = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_BASAL";

    public static final String EXTRA_DATUM = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_DATUM";
    public static final String EXTRA_UHRZEIT = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_UHRZEIT";
    public static final String EXTRA_CURRENT_TIME_MILLIS = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_CURRENT_TIME_MILLIS";
    public static final String EXTRA_EINTRAG_DATUM_MILLIS = "com.karstenfischer.room.roomdatabasegithubtest.EXTRA_EINTRAG_DATUM_MILLIS";

    private EditText etTitle;
    private EditText etDescription;
    private NumberPicker npPriority;

    private EditText etBlutzucker;
    private EditText etBe;
    private EditText etBolus;
    private EditText etKorrektur;
    private EditText etBasal;

    private TextView tvDatum;
    private TextView tvUhrzeit;
    private TextView tvCurrentTimeMillis;
    private TextView tveintragDatumMillis;

    private String automatischOderManuell;

    private int blutzuckerHint;
    private float beHint;
    private float bolusHint;
    private float korrekturHint;
    private float basalHint;

    private String datum;
    private String uhrzeit;
    private long currentTimeMillis;
    private long eintragDatumMillis;

    private FloatingActionButton fabDate;
    private FloatingActionButton fabTime;

    private SimpleDateFormat simpleDateFormatDatum = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
    private SimpleDateFormat simpleDateFormatUhrzeit = new SimpleDateFormat("HH:mm", Locale.GERMAN);




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //Wichtig zum Reden!!!
        TTS.init(getApplicationContext());

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        npPriority = findViewById(R.id.npPriority);

        etBlutzucker = findViewById(R.id.etBlutzucker);
        etBe = findViewById(R.id.etBe);
        etBolus = findViewById(R.id.etBolus);
        etKorrektur = findViewById(R.id.etKorrektur);
        etBasal = findViewById(R.id.etBasal);

        fabDate = findViewById(R.id.fabDate);
        fabTime = findViewById(R.id.fabTime);

        tvDatum = findViewById(R.id.tvDatum);
        tvUhrzeit = findViewById(R.id.tvUhrzeit);
        tvCurrentTimeMillis = findViewById(R.id.tvCurrentTimeMillis);
        tveintragDatumMillis = findViewById(R.id.tvEintragDatumMillis);


        npPriority.setMinValue(1);
        npPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        fabDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatumEingeben();
            }
        });

        fabTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UhrzeitEingeben();

            }
        });

        //datum = simpleDateFormatDatum.format(new Date());
        //uhrzeit = simpleDateFormatUhrzeit.format(new Date());


        Intent intent = getIntent();


        if (intent.hasExtra(EXTRA_ID)) {
            automatischOderManuell = "manuell";
            setTitle("Eintrag ändern");
            etTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            etDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            npPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));


            blutzuckerHint = intent.getIntExtra(EXTRA_BLUTZUCKER, 0);
            etBlutzucker.setHint(String.valueOf(blutzuckerHint));
            etBlutzucker.setHintTextColor(getResources().getColor(R.color.schriftGrauHell));

            beHint = intent.getFloatExtra(EXTRA_BE, 0);
            etBe.setHint(String.valueOf(beHint));
            etBe.setHintTextColor(getResources().getColor(R.color.schriftGrauHell));

            bolusHint = intent.getFloatExtra(EXTRA_BOLUS, 0);
            etBolus.setHint(String.valueOf(bolusHint));
            etBolus.setHintTextColor(getResources().getColor(R.color.schriftGrauHell));

            korrekturHint = intent.getFloatExtra(EXTRA_KORREKTUR, 0);
            etKorrektur.setHint(String.valueOf(korrekturHint));
            etKorrektur.setHintTextColor(getResources().getColor(R.color.schriftGrauHell));

            basalHint = intent.getFloatExtra(EXTRA_BASAL, 0);
            etBasal.setHint(String.valueOf(basalHint));
            etBasal.setHintTextColor(getResources().getColor(R.color.schriftGrauHell));

            tvDatum.setText(intent.getStringExtra(EXTRA_DATUM));
            tvUhrzeit.setText(intent.getStringExtra(EXTRA_UHRZEIT));
            currentTimeMillis = intent.getLongExtra(EXTRA_CURRENT_TIME_MILLIS, 0);
            eintragDatumMillis = intent.getLongExtra(EXTRA_EINTRAG_DATUM_MILLIS, 0);


        } else {
            //Neuer Eintrag
            automatischOderManuell = "automatisch";
            TTS.speak("intent automatisch");
            setTitle("Neuer Eintrag");

            datum = simpleDateFormatDatum.format(new Date());
            uhrzeit = simpleDateFormatUhrzeit.format(new Date());

            tvDatum.setText(datum);
            tvUhrzeit.setText(uhrzeit);
        }
        //ENDE Neuer Eintrag
    }


    //Eintrag ist fertig und wird gespeichert
    private void saveNote() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        int priority = npPriority.getValue();

        int blutzucker;
        if (etBlutzucker.getText().toString().isEmpty()) {
            blutzucker = blutzuckerHint;
        } else {
            blutzucker = Integer.parseInt(etBlutzucker.getText().toString());
        }


        float be;
        if (etBe.getText().toString().isEmpty()) {
            be = beHint;
        } else {
            be = Float.parseFloat(etBe.getText().toString());
        }


        float bolus;
        if (etBolus.getText().toString().isEmpty()) {
            bolus = bolusHint;
        } else {
            bolus = Float.parseFloat(etBolus.getText().toString());
        }


        float korrektur;
        if (etKorrektur.getText().toString().isEmpty()) {
            korrektur = korrekturHint;
        } else {
            korrektur = Float.parseFloat(etKorrektur.getText().toString());
        }


        float basal;
        if (etBasal.getText().toString().isEmpty()) {
            basal = basalHint;
        } else {
            basal = Float.parseFloat(etBasal.getText().toString());
        }

        title = etTitle.getText().toString();
        description = etDescription.getText().toString();

        datum = tvDatum.getText().toString();
        uhrzeit = tvUhrzeit.getText().toString();


        Intent data = new Intent();

        //Datum und Uhrzeit wurden nicht verändert: AUTOMATISCH)
        if (automatischOderManuell.equals("automatisch")) {
            //TTS.speak("das ist gut. automatic");
            datum = simpleDateFormatDatum.format(new Date());
            uhrzeit = simpleDateFormatUhrzeit.format(new Date());
            currentTimeMillis = System.currentTimeMillis();
        }

        //Manuell
        else {
            datum = tvDatum.getText().toString();
            uhrzeit = tvUhrzeit.getText().toString();
            String datumUndUhrzeit = datum + "-" + uhrzeit + ":00";

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss", Locale.GERMAN);
            try {
                Date startDate = simpleDateFormat.parse(datumUndUhrzeit);
                currentTimeMillis = startDate != null ? startDate.getTime() : 0;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Date startDate;
        try {
            startDate = simpleDateFormatDatum.parse(datum);
            eintragDatumMillis = startDate != null ? startDate.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Datum, Uhrzeit, currentTimeMillis, eintragDatumMillis ermitteln



        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        data.putExtra(EXTRA_BLUTZUCKER, blutzucker);
        data.putExtra(EXTRA_BE, be);
        data.putExtra(EXTRA_BOLUS, bolus);
        data.putExtra(EXTRA_KORREKTUR, korrektur);
        data.putExtra(EXTRA_BASAL, basal);

        data.putExtra(EXTRA_DATUM, datum);
        data.putExtra(EXTRA_UHRZEIT, uhrzeit);
        data.putExtra(EXTRA_CURRENT_TIME_MILLIS, currentTimeMillis);
        data.putExtra(EXTRA_EINTRAG_DATUM_MILLIS, eintragDatumMillis);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        Intent intent1=new Intent(getApplicationContext(),EintragInFirestore.class);
        intent1.putExtra("Blutzucker", blutzucker);
        TTS.speak("blut eins"+blutzucker);
        intent1.putExtra("Broteinheiten", be);
        intent1.putExtra("Bolus", bolus);
        intent1.putExtra("Korrektur", korrektur);
        intent1.putExtra("Basal", basal);

        startActivity(intent1);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void DatumEingeben() {
        TTS.speak("date picker");
        DialogFragment datePicker = new DateFragment();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        datum = tvDatum.getText().toString();
        editor.putString("datum", datum);
        //TTS.speak("date 1 is" + datum);
        editor.apply();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //Dasselbe Datum anzeigen!!!
        datum = dayOfMonth + "." + (month + 1) + "." + year;
        tvDatum.setText(datum);
    }

    private void UhrzeitEingeben() {
        DialogFragment timePicker = new TimeFragment();
        TTS.speak("time picker");
        automatischOderManuell = "manuell";
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putString("automatischOderManuell", automatischOderManuell);

        uhrzeit = tvUhrzeit.getText().toString();
        editor.putString("uhrzeit", uhrzeit);
        //TTS.speak("clock button is" + uhrzeit);
        editor.apply();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String minuteString;
        String stundeString;

        if (minute < 10) {
            minuteString = "0" + minute;
        } else {
            minuteString = "" + minute;
        }
        if (hourOfDay < 10) {
            stundeString = "0" + hourOfDay;
        } else {
            stundeString = "" + hourOfDay;
        }
        //todo uhrzeitUndSekunde = stundeString + ":" + minuteString + ":00";
        //Aktuelle Uhrzeit
        uhrzeit = stundeString + ":" + minuteString;
        //TTS.speak("Die uhr sagt"+uhrzeit);
        tvUhrzeit.setText(uhrzeit);
    }
}
