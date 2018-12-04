package de.hochrad.hochradapp.activities.substitution;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.domain.Vertretung;
import de.hochrad.hochradapp.domain.Vertretungsplan;
import de.hochrad.hochradapp.hilfsfunktionen.FileWR;
import de.hochrad.hochradapp.hilfsfunktionen.Logic;
import de.hochrad.hochradapp.loader.VertretungsplanLadenTask;
import de.hochrad.hochradapp.loader.VertretungsplanLadenTaskCallBack;

import java.io.File;

public class DetailSubstitutionScheduleActivity extends AppCompatActivity implements VertretungsplanLadenTaskCallBack {
    private Context context;
    private FileWR fileWR;

    class SubstitutionScheduleAdapter extends Adapter<SubstitutionScheduleAdapter.ViewHolder> {
        private Vertretungsplan vertretungsplan;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView art;
            TextView fach;
            TextView informationen;
            TextView raumbezeichnung;
            TextView stunde;
            TextView wochentag;

            ViewHolder(View itemView) {
                super(itemView);
                this.stunde = itemView.findViewById(R.id.Stunde);
                this.art = itemView.findViewById(R.id.Art);
                this.fach = itemView.findViewById(R.id.Fach);
                this.raumbezeichnung = itemView.findViewById(R.id.Raumbezeichnung);
                this.informationen = itemView.findViewById(R.id.Informationen);
                this.wochentag = itemView.findViewById(R.id.Wochentag);
            }
        }

        SubstitutionScheduleAdapter(Vertretungsplan vertretungsplan) {
            this.vertretungsplan = vertretungsplan;
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_substitution, parent, false));
        }

        private void textViewAsHeadline(TextView tw) {
            tw.setTypeface(tw.getTypeface(), 1);
        }

        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (position == 0) {
                textViewAsHeadline(holder.stunde);
                textViewAsHeadline(holder.art);
                textViewAsHeadline(holder.fach);
                textViewAsHeadline(holder.raumbezeichnung);
                textViewAsHeadline(holder.informationen);
                textViewAsHeadline(holder.wochentag);
                return;
            }
            Vertretung vertretung = this.vertretungsplan.vertretungen.get(position - 1);
            holder.wochentag.setText(vertretung.Wochentag.toString());
            holder.stunde.setText(vertretung.Stunde.toString());
            holder.fach.setText(vertretung.Fach.toString());
            holder.raumbezeichnung.setText(vertretung.Raum.toString());
            holder.art.setText(vertretung.Art.toString());
            holder.informationen.setText(vertretung.Informationen.toString());
        }

        public int getItemCount() {
            return this.vertretungsplan.vertretungen.size() + 1;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.view_substitution_schedule);
        this.context = this;
        this.fileWR = new FileWR();
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
        if (mitklassenauswahl) {
            new VertretungsplanLadenTask(wochennummer + wochenauswahl, this).execute(Logic.fiveDigits(klassenauswahl));
            return;
        }
        VertretungsplanLadenTask vertretungsplanLadenTask = new VertretungsplanLadenTask(wochennummer + wochenauswahl, this);
        String[] strArr = new String[1];
        FileWR fileWR = this.fileWR;
        String stringBuilder = this.context.getFilesDir() + File.separator + "auswahl";
        strArr[0] = Logic.fiveDigits(fileWR.ladeFile(stringBuilder));
        vertretungsplanLadenTask.execute(strArr);
    }

    public void vertretungsplanLaden(int hash, Vertretungsplan vertretungsplan) {
        if (vertretungsplan == null) {
            super.onBackPressed();
            return;
        }
        FileWR fileWR = this.fileWR;
        String stringBuilder = this.context.getFilesDir() +
                File.separator +
                "vertretungsplanhash";
        fileWR.writeFile(hash, stringBuilder);
        if (vertretungsplan.klasse == null) {
            super.onBackPressed();
        }
        setTitle(vertretungsplan.klasse.toString());
        RecyclerView vertretungen = findViewById(R.id.substitutions_schedule);
        if (vertretungsplan.vertretungen.size() != 0) {
            vertretungen.setAdapter(new SubstitutionScheduleAdapter(vertretungsplan));
        } else {
            setTitle(getString(R.string.no_substituions));
        }
        vertretungen.setLayoutManager(new LinearLayoutManager(this.context));
    }
}
