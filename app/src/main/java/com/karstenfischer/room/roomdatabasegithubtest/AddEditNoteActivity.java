package com.karstenfischer.room.roomdatabasegithubtest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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


 private    int blutzuckerHint;
 private    float beHint;
  private   float bolusHint;
  private   float korrekturHint;
  private   float basalHint;

  private FloatingActionButton fabDate;
    private FloatingActionButton fabTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        tvDatum= findViewById(R.id.tvDatum);
        tvUhrzeit= findViewById(R.id.tvUhrzeit);
        tvCurrentTimeMillis= findViewById(R.id.tvCurrentTimeMillis);
        tveintragDatumMillis= findViewById(R.id.tvEintragDatumMillis);




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



        Intent intent = getIntent();


        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
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


        } else {
            setTitle("Add Note");
        }
    }





    private void saveNote() {
        String title = etTitle.getText().toString();
        String descrition = etDescription.getText().toString();
        int priority = npPriority.getValue();
        int blutzucker;
        float be;
        float bolus;
        float korrektur;
        float basal;


        if (etBlutzucker.getText().toString().isEmpty()) {
            blutzucker = blutzuckerHint;
        } else {
            blutzucker = Integer.parseInt(etBlutzucker.getText().toString());
        }


        if (etBe.getText().toString().isEmpty()) {
            be = beHint;
        } else {
            be = Float.parseFloat(etBe.getText().toString());
        }


        if (etBolus.getText().toString().isEmpty()) {
            bolus = bolusHint;
        } else {
            bolus = Float.parseFloat(etBolus.getText().toString());
        }


        if (etKorrektur.getText().toString().isEmpty()) {
            korrektur = korrekturHint;
        } else {
            korrektur = Float.parseFloat(etKorrektur.getText().toString());
        }


        if (etBasal.getText().toString().isEmpty()) {
            basal = basalHint;
        } else {
            basal = Float.parseFloat(etBasal.getText().toString());
        }


        //if(title.trim().isEmpty()||descrition.trim().isEmpty()){
        //    Toast.makeText(this, "Please insert title and description", Toast.LENGTH_SHORT).show();
        //    return;
        //}

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, descrition);
        data.putExtra(EXTRA_PRIORITY, priority);

        data.putExtra(EXTRA_BLUTZUCKER, blutzucker);
        data.putExtra(EXTRA_BE, be);
        data.putExtra(EXTRA_BOLUS, bolus);
        data.putExtra(EXTRA_KORREKTUR, korrektur);
        data.putExtra(EXTRA_BASAL, basal);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

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
        DialogFragment datePicker = new DateFragment();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        //todo datum = tvDatum.getText().toString();
        //uhrzeit=tvUhrzeit.getText().toString();
        //todo editor.putString("datum", datum);

        //TTS.speak("date 1 is" + datum);
        editor.apply();
        //editor.putString("automatischOderManuell", automatischOderManuell);
        ////editor.putString("datum", datum);
        ////editor.putString("uhrzeit", uhrzeit);
        //editor.apply();

        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    private void UhrzeitEingeben() {
        DialogFragment timePicker = new TimeFragment();
        //TTS.speak("time picker");
        //todo automatischOderManuell = "manuell";
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        //todo editor.putString("automatischOderManuell", automatischOderManuell);
        //datum=tvDatum.getText().toString();
        //todo uhrzeit = tvUhrzeit.getText().toString();

        //editor.putString("datum", datum);
        //todo editor.putString("uhrzeit", uhrzeit);
        //TTS.speak("clock button is" + uhrzeit);
        editor.apply();

        //automatischOderManuell = "manuell";
        //editor = sharedPreferences.edit();
        //editor.putString("automatischOderManuell", automatischOderManuell);
        //editor.putString("datum", datum);
        //editor.putString("uhrzeit", uhrzeit);
        //editor.apply();

        timePicker.show(getSupportFragmentManager(), "time picker");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //Calendar c = Calendar.getInstance();
        //c.set(Calendar.YEAR, year);
        //c.set(Calendar.MONTH, month);
        //c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //String datumLang = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        //Aktuelles Datum
        //datum = DateFormat.getDateInstance().format(c.getTime());

        //Dasselbe Datum anzeigen!!!
        //todo datum = dayOfMonth + "." + (month + 1) + "." + year;
        //todo tvDatum.setText(datum);
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
        //todo uhrzeit = stundeString + ":" + minuteString;
        //TTS.speak("Die uhr sagt"+uhrzeit);
        //todo tvUhrzeit.setText(uhrzeit);
    }
}
