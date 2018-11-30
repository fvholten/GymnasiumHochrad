package de.hochrad.hochradapp.activities.vertretungsplan;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.domain.Vertretungsplan;
import de.hochrad.hochradapp.hilfsfunktionen.FileWR;
import de.hochrad.hochradapp.hilfsfunktionen.Logic;
import de.hochrad.hochradapp.loader.VertretungsplanLadenTask;
import de.hochrad.hochradapp.loader.VertretungsplanLadenTaskCallBack;

import java.io.File;

public class DetailVertretungsplanActivity extends AppCompatActivity implements VertretungsplanLadenTaskCallBack {

    private Context context;
    private FileWR fileWR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertretungsplan);
        context = this;
        fileWR = new FileWR();


        boolean mitklassenauswahl = false;
        int klassenauswahl = 0;
        int wochenauswahl = 0;
        int wochennummer = 0;

        Bundle data = getIntent().getExtras();
        if (data != null) {
            if (data.containsKey(getString(R.string.klassenauswahl_key))) {
                klassenauswahl = data.getInt(getString(R.string.klassenauswahl_key));
            }
            if (data.containsKey(getString(R.string.mitklassenauswahl_key))) {
                mitklassenauswahl = data.getBoolean(getString(R.string.mitklassenauswahl_key));
            }
            if (data.containsKey(getString(R.string.wochenauswahl_key))) {
                wochenauswahl = data.getInt(getString(R.string.wochenauswahl_key));
            }
            if (data.containsKey(getString(R.string.wochennummer_key))) {
                wochennummer = data.getInt(getString(R.string.wochennummer_key));
            }
        }

        VertretungsplanLadenTask vertretungsplanLadenTask;
        if (mitklassenauswahl) {
            vertretungsplanLadenTask = new VertretungsplanLadenTask(wochennummer + wochenauswahl, this);
            vertretungsplanLadenTask.execute(Logic.fiveDigits(klassenauswahl));
        } else {
            vertretungsplanLadenTask = new VertretungsplanLadenTask(wochennummer + wochenauswahl, this);
            vertretungsplanLadenTask.execute(Logic.fiveDigits(fileWR.ladeFile(context.getFilesDir() + File.separator + "auswahl")));
        }
    }

    @Override
    public void vertretungsplanLaden(int hash, Vertretungsplan vertretungsplan) {
        if (vertretungsplan == null) {
            super.onBackPressed();
        } else {
            fileWR.writeFile(hash, context.getFilesDir() + File.separator + "vertretungsplanhash");
            TextView klasse = findViewById(R.id.Klasse);
            if (vertretungsplan.klasse == null) {
                super.onBackPressed();
            }
            klasse.setText(vertretungsplan.klasse.toString());
            RecyclerView vertretungen = findViewById(R.id.vertretungsplan);
            if (vertretungsplan.Vertretungen.size() != 0) {
                vertretungen.setAdapter(new VertretungsplanAdapter(context, vertretungsplan));
            } else {
                TextView keineVertretungen = findViewById(R.id.keineVertretungen);
                keineVertretungen.setVisibility(View.VISIBLE);
                keineVertretungen.setText("Aktuell gibt es keine Vertretungen.");
            }
            vertretungen.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
