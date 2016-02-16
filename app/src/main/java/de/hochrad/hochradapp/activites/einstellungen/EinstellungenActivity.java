package de.hochrad.hochradapp.activites.einstellungen;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import de.hochrad.hochradapp.hilfsfunktionen.ConnectionTest;
import de.hochrad.hochradapp.activites.startseite.MainActivity;
import de.hochrad.hochradapp.activites.StundenzeitenActivity;
import de.hochrad.hochradapp.activites.UeberActivity;
import de.hochrad.hochradapp.activites.WochenplanActivtiy;
import de.hochrad.hochradapp.activites.vertretungsplan.VertretungsplanActivity;
import de.hochrad.hochradapp.loader.KlassenLadenTask;
import de.hochrad.hochradapp.loader.KlassenLadenTaskCallBack;
import de.hochrad.hochradapp.hilfsfunktionen.Optionen;
import de.hochrad.hochradapp.R;

public class EinstellungenActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        KlassenLadenTaskCallBack {

    TextView auswahl;
    Optionen optionen;

    Spinner klassenspinner;
    ArrayAdapter<String> klassenauswahlAdapter;
    Context context = this;


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
        if (!ConnectionTest.isOnline(context)) {
            startActivity();
        }
        new KlassenLadenTask(0, this).execute();
    }

    @Override
    public void KlassenLaden(int wochenauswahl, String[] klassen) {
        if (klassen == null) {
            startActivity();
        } else {
            optionen = new Optionen(context, "auswahl");
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            klassenauswahlAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
            klassenauswahlAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            klassenauswahlAdapter.add("Wähle deine Klasse!");
            klassenauswahlAdapter.addAll(klassen);
            klassenspinner = (Spinner) findViewById(R.id.klassenauswahlspinner);
            klassenspinner.setAdapter(klassenauswahlAdapter);
            auswahl = (TextView) findViewById(R.id.wahl);
            auswahl.setText(getString(R.string.aktuelleÜberwachung) + klassenspinner.getItemAtPosition(optionen.getFile()).toString());
            klassenspinner.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int positionklassenauswahl, long id) {
                            if (positionklassenauswahl == 0) {
                                return;
                            }
                            Toast.makeText(context, "Gespeichert.", Toast.LENGTH_SHORT).show();
                            optionen.putFile(positionklassenauswahl, "auswahl");
                            auswahl.setText(getString(R.string.Einstellungengespeichert) + klassenspinner.getItemAtPosition(optionen.getFile()).toString());
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
        if (id == R.id.startseite) {
            startActivity(new Intent(context, MainActivity.class));
            finish();
        } else if (id == R.id.vertretungsplan) {
            startActivity(new Intent(context, VertretungsplanActivity.class));
            finish();
        } else if (id == R.id.einstellungen) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.wochenplan) {
            startActivity(new Intent(context, WochenplanActivtiy.class));
            finish();
        } else if (id == R.id.stundenzeiten) {
            startActivity(new Intent(context, StundenzeitenActivity.class));
            finish();
        } else if (id == R.id.ueber) {
            startActivity(new Intent(context, UeberActivity.class));
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
