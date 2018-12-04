package de.hochrad.hochradapp.activities.substitution;

import android.content.Context;
import android.content.Intent;
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
import de.hochrad.hochradapp.loader.KlassenLadenTask;
import de.hochrad.hochradapp.loader.KlassenLadenTaskCallBack;
import de.hochrad.hochradapp.loader.WochennummerLadenTask;
import de.hochrad.hochradapp.loader.WochennummerLadenTaskCallBack;

import java.util.Arrays;
import java.util.List;

public class DetailKlassenauswahlActivity extends AppCompatActivity implements KlassenLadenTaskCallBack, WochennummerLadenTaskCallBack, SchoolClassClickedCallback {
    private Context context;
    private WochennummerLadenTask wochennummerLadenTask;

    class KlassenAdapter extends Adapter<KlassenAdapter.ViewHolder> {
        private SchoolClassClickedCallback schoolClassClickedCallback;
        private List<String> school_classes;
        private int wochenauswahl;

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView schoolClassTextView;
            private View view;

            ViewHolder(View itemView, TextView schoolClassTextView) {
                super(itemView);
                this.schoolClassTextView = schoolClassTextView;
                this.view = itemView;
            }

            TextView getSchoolClassTextView() {
                return this.schoolClassTextView;
            }

            View getView() {
                return this.view;
            }
        }

        KlassenAdapter(List<String> school_classes, int wochenauswahl, SchoolClassClickedCallback schoolClassClickedCallback) {
            this.school_classes = school_classes;
            this.wochenauswahl = wochenauswahl;
            this.schoolClassClickedCallback = schoolClassClickedCallback;
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_class_chooser, parent, false);
            return new ViewHolder(view, (TextView) view.findViewById(R.id.school_class_text_view));
        }

        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            holder.getSchoolClassTextView().setText(this.school_classes.get(position));
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DetailKlassenauswahlActivity.this.wochennummerLadenTask == null) {
                        schoolClassClickedCallback.onClick(holder.getAdapterPosition(), wochenauswahl);
                    }
                }
            });

        }

        public int getItemCount() {
            return this.school_classes.size();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.view_class_chooser);
        this.context = this;
        setTitle(getString(R.string.klassenauswahlh));
        Bundle data = getIntent().getExtras();
        if (data == null || !data.containsKey(getString(R.string.selected_item_key))) {
            finish();
        } else {
            new KlassenLadenTask(data.getInt(getString(R.string.selected_item_key)) - 1, this).execute();
        }
    }

    public void klassenLaden(int wochenauswahl, String[] klassen) {
        if (klassen == null) {
            finish();
            return;
        }
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        RecyclerView klassenRecyclerView = findViewById(R.id.klassenauswahl_recycler_view);
        klassenRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        klassenRecyclerView.setAdapter(new KlassenAdapter(Arrays.asList(klassen), wochenauswahl, this));
    }

    public void onClick(int position, int wochenauswahl) {
        if (this.wochennummerLadenTask == null) {
            this.wochennummerLadenTask = new WochennummerLadenTask(true, position, wochenauswahl, this);
            this.wochennummerLadenTask.execute();
        }
    }

    @Override
    public void loadWeekNumber(boolean mitklassenauswahl, int klassenauswahl, int wochenauswahl, Integer wochennummer) {
        if (wochennummer == null) {
            finish();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.mitklassenauswahl_key), mitklassenauswahl);
        bundle.putInt(getString(R.string.klassenauswahl_key), klassenauswahl);
        bundle.putInt(getString(R.string.wochenauswahl_key), wochenauswahl);
        bundle.putInt(getString(R.string.wochennummer_key), wochennummer.intValue());
        Intent intent = new Intent(this, DetailSubstitutionScheduleActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
