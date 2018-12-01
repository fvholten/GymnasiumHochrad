package de.hochrad.hochradapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activities.vertretungsplan.DetailKlassenauswahlActivity;
import de.hochrad.hochradapp.activities.vertretungsplan.DetailVertretungsplanActivity;
import de.hochrad.hochradapp.hilfsfunktionen.ConnectionTest;
import de.hochrad.hochradapp.loader.WochenLadenTask;
import de.hochrad.hochradapp.loader.WochenLadenTaskCallBack;
import de.hochrad.hochradapp.loader.WochennummerLadenTask;
import de.hochrad.hochradapp.loader.WochennummerLadenTaskCallBack;

public class VertretungsplanFragment extends Fragment implements
        WochenLadenTaskCallBack, WochennummerLadenTaskCallBack {

    private Context context;
    private View view;

    public VertretungsplanFragment() {
    }

    private DetailView detailView;

    public interface DetailView {
        void openDetails(Class toActivity, Bundle bundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wochenplanauswahl, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        context = getContext();

        if (!(ConnectionTest.isOnline(context))) {
            startActivity();
        }

        new WochenLadenTask(this).execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        detailView = (DetailView) context;
    }

    private ArrayAdapter<String> getWeekDropdownAdapter(String[] weeks) {
        ArrayAdapter<String> weekDropdownAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
        weekDropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekDropdownAdapter.add(getString(R.string.choose_a_week));
        weekDropdownAdapter.addAll(weeks);
        return weekDropdownAdapter;
    }

    @Override
    public void attachWeeks(String[] weeks) {
        if (weeks != null) {

            // DEINE KLASSE
            Spinner deineKlasse = view.findViewById(R.id.deineKlasse);
            deineKlasse.setAdapter(getWeekDropdownAdapter(weeks));
            deineKlasse.setOnItemSelectedListener
                    (new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int selectedItem, long id) {
                            if (selectedItem == 0)
                                return;

                            if (!(ConnectionTest.isOnline(context))) {
                                startActivity();
                            }

                            new WochennummerLadenTask(false, 0, selectedItem - 1, VertretungsplanFragment.this).execute();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

            //ALLE KLASSEN
            Spinner week = view.findViewById(R.id.week);
            week.setAdapter(getWeekDropdownAdapter(weeks));
            week.setOnItemSelectedListener
                    (new AdapterView.OnItemSelectedListener() {
                         @Override
                         public void onItemSelected(AdapterView<?> parent, View view,
                                                    int selectedItem, long id) {
                             if (selectedItem == 0)
                                 return;

                             Bundle bundle = new Bundle();
                             bundle.putInt(getString(R.string.selected_item_key), selectedItem);

                             detailView.openDetails(DetailKlassenauswahlActivity.class, bundle);
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
            startActivity();
        } else {

            Bundle bundle = new Bundle();
            bundle.putBoolean(getString(R.string.mitklassenauswahl_key), mitklassenauswahl);
            bundle.putInt(getString(R.string.klassenauswahl_key), klassenauswahl);
            bundle.putInt(getString(R.string.wochenauswahl_key), wochenauswahl);
            bundle.putInt(getString(R.string.wochennummer_key), wochennummer);

            detailView.openDetails(DetailVertretungsplanActivity.class, bundle);
        }
    }


    private void startActivity() {
        //startActivity(new Intent(context, VertretungsplanActivityLoadscreenActivity.class));
    }
}
