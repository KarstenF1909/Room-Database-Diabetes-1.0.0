package com.karstenfischer.room.roomdatabasegithubtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

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

    int blutzuckerHint;
    float beHint;
    float bolusHint;
    float korrekturHint;
    float basalHint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        npPriority = findViewById(R.id.npPriority);

        etBlutzucker = findViewById(R.id.etBlutzucker);
        etBe = findViewById(R.id.etBe);
        etBolus = findViewById(R.id.etBolus);
        etKorrektur = findViewById(R.id.etKorrektur);
        etBasal = findViewById(R.id.etBasal);

        npPriority.setMinValue(1);
        npPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();


        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            etTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            etDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            npPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));


            blutzuckerHint = intent.getIntExtra(EXTRA_BLUTZUCKER, 0);
            etBlutzucker.setHint(String.valueOf(blutzuckerHint));

            beHint = intent.getFloatExtra(EXTRA_BE, 0);
            etBe.setHint(String.valueOf(beHint));

            bolusHint = intent.getFloatExtra(EXTRA_BOLUS, 0);
            etBolus.setHint(String.valueOf(bolusHint));

            korrekturHint = intent.getFloatExtra(EXTRA_KORREKTUR, 0);
            etKorrektur.setHint(String.valueOf(korrekturHint));

            basalHint = intent.getFloatExtra(EXTRA_BASAL, 0);
            etBasal.setHint(String.valueOf(basalHint));


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
}
