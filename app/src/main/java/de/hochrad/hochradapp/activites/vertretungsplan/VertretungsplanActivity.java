package de.hochrad.hochradapp.activites.vertretungsplan;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.StundenzeitenActivity;
import de.hochrad.hochradapp.activites.UeberActivity;
import de.hochrad.hochradapp.activites.WochenplanActivtiy;
import de.hochrad.hochradapp.activites.einstellungen.EinstellungenActivity;
import de.hochrad.hochradapp.activites.startseite.MainActivity;
import de.hochrad.hochradapp.domain.Vertretungsplan;
import de.hochrad.hochradapp.hilfsfunktionen.ConnectionTest;
import de.hochrad.hochradapp.hilfsfunktionen.Logic;
import de.hochrad.hochradapp.hilfsfunktionen.Optionen;
import de.hochrad.hochradapp.loader.KlassenLadenTask;
import de.hochrad.hochradapp.loader.KlassenLadenTaskCallBack;
import de.hochrad.hochradapp.loader.VertretungsplanLadenTask;
import de.hochrad.hochradapp.loader.VertretungsplanLadenTaskCallBack;
import de.hochrad.hochradapp.loader.WochenLadenTask;
import de.hochrad.hochradapp.loader.WochenLadenTaskCallBack;
import de.hochrad.hochradapp.loader.WochennummerLadenTask;
import de.hochrad.hochradapp.loader.WochennummerLadenTaskCallBack;

public class VertretungsplanActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        WochennummerLadenTaskCallBack,
        WochenLadenTaskCallBack,
        VertretungsplanLadenTaskCallBack,
        KlassenLadenTaskCallBack {

    Context context = this;
    Optionen optionen;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        NavigationDrawer und ToolBar!!!!
        setContentView(R.layout.activity_wochenauswahl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//       Dass, was auf dem Bildschirmentstehen soll
        if (!(ConnectionTest.isOnline(context))) {
            startActivity();
        }
        new WochenLadenTask(this).execute();
    }

    @Override
    public void WochenLaden(String[] wochen) {

        if (wochen == null) {
            startActivity();
        } else {
            ArrayAdapter<String> wochenauswahlApdatper = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
            wochenauswahlApdatper.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            ArrayAdapter<String> wochenauswahlAdapterDeineKlasse = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
            wochenauswahlAdapterDeineKlasse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            wochenauswahlApdatper.add(getString(R.string.WähleeineWoche));
            wochenauswahlApdatper.addAll(wochen);
            wochenauswahlAdapterDeineKlasse.add(getString(R.string.WähleeineWoche));
            wochenauswahlAdapterDeineKlasse.addAll(wochen);

// DEINE KLASSE
            optionen = new Optionen(context, "auswahl");
            Spinner deineKlasse = (Spinner) findViewById(R.id.deineKlasse);
            deineKlasse.setAdapter(wochenauswahlAdapterDeineKlasse);
            deineKlasse.setOnItemSelectedListener
                    (new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int selectedItem, long id) {
                            if (selectedItem == 0)
                                return;

                            if (!(ConnectionTest.isOnline(context))) {
                                startActivity();
                            }
                            new WochennummerLadenTask(false, 0, selectedItem - 1, VertretungsplanActivity.this).execute();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
//ALLE KLASSEN
            Spinner week = (Spinner) findViewById(R.id.week);
            week.setAdapter(wochenauswahlApdatper);
            week.setOnItemSelectedListener
                    (new AdapterView.OnItemSelectedListener() {
                         @Override
                         public void onItemSelected(AdapterView<?> parent, View view,
                                                    int selectedItem, long id) {
                             if (selectedItem == 0)
                                 return;
                             setContentView(R.layout.klassenauswahl);
                             Button button = (Button) findViewById(R.id.button);
                             button.setOnClickListener(
                                     new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             recreate();
                                         }
                                     }
                             );
                             if (!(ConnectionTest.isOnline(context))) {
                                 startActivity();
                             }
                             new KlassenLadenTask(selectedItem - 1, VertretungsplanActivity.this).execute();
                         }

                         @Override
                         public void onNothingSelected(AdapterView<?> parent) {
                         }
                     }
                    );
        }

    }

    @Override
    public void KlassenLaden(final int wochenauswahl, String[] klassen) {

        if (klassen == null) {
            startActivity();
        } else {
            Toast.makeText(context, "Lade...", Toast.LENGTH_SHORT).show();
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);

            ArrayAdapter<String> klassenauswahlAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
            klassenauswahlAdapter.add("Wähle deine Klasse!");
            klassenauswahlAdapter.addAll(klassen);

            Spinner klassenspinner = (Spinner) findViewById(R.id.spinner); // zeigt den Inhalt von klassenauswahl und Wochenwahl an
            klassenspinner.setAdapter(klassenauswahlAdapter);

            klassenspinner.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int klassenauswahl, long id) {
                            if (klassenauswahl == 0) {
                                return;
                            }
                            if (!(ConnectionTest.isOnline(context))) {
                                startActivity();
                            }
                            new WochennummerLadenTask(true, klassenauswahl, wochenauswahl, VertretungsplanActivity.this).execute();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    }
            );
        }
    }

    @Override
    public void WochennummerLaden(boolean mitklassenauswahl, int klassenauswahl, int wochenauswahl, Integer wochennummer) {
        if (wochennummer == null) {
            startActivity();
        } else {
            setContentView(R.layout.vertretungsplan);
            if (mitklassenauswahl) {
                new VertretungsplanLadenTask(wochennummer + wochenauswahl, this).execute(Logic.fiveDigits(klassenauswahl));
            } else {
                new VertretungsplanLadenTask(wochennummer + wochenauswahl, this).execute(Logic.fiveDigits(optionen.getTeilseite()));
            }
        }
    }

    @Override
    public void VertretungsplanLaden(Vertretungsplan vertretungsplan) {
        if (vertretungsplan == null) {
            startActivity();
        } else {
            TextView klasse = (TextView) findViewById(R.id.Klasse);
            if (vertretungsplan.Klasse == null) {
                startActivity();
                finish();
            }
            klasse.setText(vertretungsplan.Klasse.toString());
            RecyclerView vertretungen = (RecyclerView) findViewById(R.id.vertretungsplan);
            if (vertretungsplan.Vertretungen.size() != 0) {
                vertretungen.setAdapter(new VertretungsplanAdapter(context, vertretungsplan));
            } else {
                TextView keineVertretungen = (TextView) findViewById(R.id.keineVertretungen);
                keineVertretungen.setVisibility(View.VISIBLE);
                keineVertretungen.setText("Aktuell gibt es keine Vertretungen.");
            }
            vertretungen.setLayoutManager(new LinearLayoutManager(context));

            Button back = (Button) findViewById(R.id.back);
            back.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recreate();
                        }
                    });
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
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.einstellungen) {
            startActivity(new Intent(context, EinstellungenActivity.class));
            finish();
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


    public void startActivity() {
        startActivity(new Intent(context, VertretungsplanActivityLoadscreenActivity.class));
    }
}
