package de.hochrad.hochradapp.activites.einstellungen;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.NavigationDrawerNavigate;
import de.hochrad.hochradapp.hilfsfunktionen.ConnectionTest;
import de.hochrad.hochradapp.hilfsfunktionen.FileWR;
import de.hochrad.hochradapp.loader.KlassenLadenTask;
import de.hochrad.hochradapp.loader.KlassenLadenTaskCallBack;
import de.hochrad.hochradapp.service.Service;

public class EinstellungenActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        KlassenLadenTaskCallBack {

    TextView auswahl;
    FileWR fileWR;

    Spinner klassenspinner;
    ArrayAdapter<String> klassenauswahlAdapter;
    Context context = this;
    NavigationDrawerNavigate navigationDrawerNavigate;
    KlassenLadenTask klassenLadenTask;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        NavigationDrawer und ToolBar!!!!

        setContentView(R.layout.activity_einstellungen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        In der App
        fileWR = new FileWR();
        int switcher = fileWR.ladeFile(getFilesDir() + File.separator + "switch");
        Switch notificationswitch = (Switch) findViewById(R.id.switchNotifications);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.SwitchON);
        if (switcher == 2) {
            linearLayout.setVisibility(View.VISIBLE);
            notificationswitch.setChecked(true);
        } else {
            notificationswitch.setChecked(false);
            linearLayout.setVisibility(View.INVISIBLE);
        }
        EditText editText = (EditText) findViewById(R.id.editNotification);
        editText.setText("" + fileWR.ladeFile(getFilesDir() + File.separator + "servicezeit"));
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    Toast.makeText(context, "Gespeichert!", Toast.LENGTH_SHORT).show();
                    fileWR.writeFile(Integer.parseInt(s.toString()), getFilesDir() + File.separator + "servicezeit");
                    startService(new Intent(context, Service.class));
                }
            }
        });
        notificationswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fileWR.writeFile(2, getFilesDir() + File.separator + "switch");
                    linearLayout.setVisibility(View.VISIBLE);
                    startService(new Intent(context, Service.class));
                } else {
                    fileWR.writeFile(1, getFilesDir() + File.separator + "switch");
                    linearLayout.setVisibility(View.INVISIBLE);
                    startService(new Intent(context, Service.class));
                }
            }

        });
        if (!ConnectionTest.isOnline(context)) {
            startActivity();
        }
        klassenLadenTask = new KlassenLadenTask(0, this);
        klassenLadenTask.execute();
    }

    @Override
    public void KlassenLaden(int wochenauswahl, String[] klassen) {
        if (klassen == null) {
            startActivity();
        } else {
            fileWR = new FileWR();
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            klassenauswahlAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
            klassenauswahlAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            klassenauswahlAdapter.add("Wähle deine Klasse!");
            klassenauswahlAdapter.addAll(klassen);
            klassenspinner = (Spinner) findViewById(R.id.klassenauswahlspinner);
            klassenspinner.setAdapter(klassenauswahlAdapter);
            auswahl = (TextView) findViewById(R.id.wahl);
            auswahl.setText(getString(R.string.aktuelleÜberwachung) + klassenspinner.getItemAtPosition(fileWR.ladeFile(getFilesDir() + File.separator + "auswahl")).toString());

            klassenspinner.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int positionklassenauswahl, long id) {
                            if (positionklassenauswahl == 0) {
                                return;
                            }
                            Toast.makeText(context, "Gespeichert.", Toast.LENGTH_SHORT).show();
                            fileWR.writeFile(positionklassenauswahl, getFilesDir() + File.separator + "auswahl");
                            auswahl.setText(getString(R.string.Einstellungengespeichert) +
                                    klassenspinner.getItemAtPosition(fileWR.ladeFile(getFilesDir() + File.separator + "auswahl")).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
        }
    }

    public void startActivity() {
        startActivity(new Intent(context, EinstellungenActivityLoadscreenActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (klassenLadenTask != null)
            klassenLadenTask.cancel(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerNavigate = new NavigationDrawerNavigate(id, context);
        if (id != R.id.einstellungen)
            finish();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
