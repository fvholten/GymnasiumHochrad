package de.hochrad.hochradapp.activities.vertretungsplan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.hilfsfunktionen.ConnectionTest;
import de.hochrad.hochradapp.loader.KlassenLadenTask;
import de.hochrad.hochradapp.loader.KlassenLadenTaskCallBack;
import de.hochrad.hochradapp.loader.WochennummerLadenTask;
import de.hochrad.hochradapp.loader.WochennummerLadenTaskCallBack;

public class DetailKlassenauswahlActivity extends AppCompatActivity
        implements KlassenLadenTaskCallBack, WochennummerLadenTaskCallBack {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.klassenauswahl);
        context = this;

        if (!(ConnectionTest.isOnline(context))) {
            finish();
        }

        Bundle data = getIntent().getExtras();
        if (data != null && data.containsKey(getString(R.string.selected_item_key))) {
            new KlassenLadenTask(data.getInt(getString(R.string.selected_item_key)) - 1, this).execute();
        } else {
            finish();
        }
    }

    @Override
    public void klassenLaden(int wochenauswahl, String[] klassen) {
        if (klassen == null) {
            super.onBackPressed();
        } else {
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);

            ArrayAdapter<String> klassenauswahlAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
            klassenauswahlAdapter.add("Wähle deine klasse!");
            klassenauswahlAdapter.addAll(klassen);

            Spinner klassenspinner = findViewById(R.id.spinner); // zeigt den Inhalt von klassenauswahl und Wochenwahl an
            klassenspinner.setAdapter(klassenauswahlAdapter);

            klassenspinner.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int klassenauswahl, long id) {
                            if (klassenauswahl == 0) {
                                return;
                            }
                            if (!(ConnectionTest.isOnline(context))) {
                                DetailKlassenauswahlActivity.super.onBackPressed();
                            }
                            new WochennummerLadenTask(true, klassenauswahl, wochenauswahl, DetailKlassenauswahlActivity.this).execute();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    }
            );
        }
    }

    @Override
    public void wochennummerLaden(boolean mitklassenauswahl, int klassenauswahl, int wochenauswahl, Integer wochennummer) {
        if (wochennummer == null) {
            finish();
        } else {

            Bundle bundle = new Bundle();
            bundle.putBoolean(getString(R.string.mitklassenauswahl_key), mitklassenauswahl);
            bundle.putInt(getString(R.string.klassenauswahl_key), klassenauswahl);
            bundle.putInt(getString(R.string.wochenauswahl_key), wochenauswahl);
            bundle.putInt(getString(R.string.wochennummer_key), wochennummer);

            Intent intent = new Intent(this, DetailVertretungsplanActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
