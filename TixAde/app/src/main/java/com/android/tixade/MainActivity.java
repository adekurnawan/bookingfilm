package com.android.tixade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    String id, username;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";

    private Spinner spClass, spMovie;
    private Button btnBook;
    private EditText etJumlah;
    private RadioGroup rgAdditional;
    private RadioButton rbAdditional;

    private List<String> classCinematix, movieCinematix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);

        spClass = (Spinner) findViewById(R.id.sp_class);
        spMovie = (Spinner) findViewById(R.id.sp_movie);
        etJumlah = (EditText) findViewById(R.id.edt_jumlah);
        rgAdditional = (RadioGroup) findViewById(R.id.rg_additional);
        btnBook = (Button) findViewById(R.id.btn_book);
        classCinematix = new ArrayList<String>();
        classCinematix.add("Ekonomi");
        classCinematix.add("Regular");
        classCinematix.add("Eksekutif");

        movieCinematix = new ArrayList<String>();
        movieCinematix.add("Sexy Killers");
        movieCinematix.add("Star Wars: The last Jedi");
        movieCinematix.add("Rumah Pengabdi Setan?");
        movieCinematix.add("Interstellar");
        movieCinematix.add("Blade Runner 2049");
        movieCinematix.add("Thor: Ragnarok");
        movieCinematix.add("Pokemon: I Choose You");
        movieCinematix.add("Happy Death Day");
        movieCinematix.add("IT!");
        movieCinematix.add("Guardians Of Galaxy. Volume 2");

        ArrayAdapter<String> classCinematixAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, classCinematix);
        ArrayAdapter<String> movieCinematixAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, movieCinematix);
        spClass.setAdapter(classCinematixAdapter);
        spMovie.setAdapter(movieCinematixAdapter);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean valid = true;

                if (etJumlah.getText().length() < 1) {
                    valid = false;
                } else if (Integer.parseInt(etJumlah.getText().toString()) < 1) {
                    valid = false;
                }

                if (valid) {
                    int selectedId = rgAdditional.getCheckedRadioButtonId();
                    rbAdditional = (RadioButton) findViewById(selectedId);

                    Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                    intent.putExtra("classCinematix", spClass.getSelectedItem().toString());
                    intent.putExtra("movieCinematix", spMovie.getSelectedItem().toString());
                    intent.putExtra("additionalCinematix", rbAdditional.getText().toString());
                    intent.putExtra("jumlahCinematix", etJumlah.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_logout:
                logoutKan();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutKan() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(Login.session_status, false);
        editor.putString(TAG_ID, null);
        editor.putString(TAG_USERNAME, null);
        editor.commit();
        Intent intent = new Intent(MainActivity.this, Login.class);
        finish();
        startActivity(intent);
    }
}
