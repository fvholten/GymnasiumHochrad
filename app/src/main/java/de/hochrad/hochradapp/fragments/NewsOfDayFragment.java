package de.hochrad.hochradapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.hilfsfunktionen.ConnectionTest;
import de.hochrad.hochradapp.loader.NachrichtenDesTagesLadenTask;
import de.hochrad.hochradapp.loader.NachrichtenDesTagesLadenTaskCallBack;
import de.hochrad.hochradapp.loader.WochennummerLadenTask;
import de.hochrad.hochradapp.loader.WochennummerLadenTaskCallBack;

import java.util.List;
import java.util.Objects;

public class StartseiteFragment extends Fragment implements WochennummerLadenTaskCallBack,
        NachrichtenDesTagesLadenTaskCallBack {

    private View view;
    private WochennummerLadenTask wochennummerLadenTask;
    private NachrichtenDesTagesLadenTask nachrichtenDesTagesLadenTask;

    public StartseiteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        //        Dass, was auf dem Bildschirmentstehen soll
        if (!(ConnectionTest.isOnline(Objects.requireNonNull(getContext())))) {
            startActivity();
        }
        wochennummerLadenTask = new WochennummerLadenTask(false, 0, 0, this);
        wochennummerLadenTask.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wochennummerLadenTask != null)
            wochennummerLadenTask.cancel(true);
        if (nachrichtenDesTagesLadenTask != null)
            nachrichtenDesTagesLadenTask.cancel(true);
    }

    @Override
    public void NachrichtenDesTages(List<String> newsDerWoche) {
        if (newsDerWoche == null) {
            startActivity();
        } else {
            ArrayAdapter<String> nachrichtenzumTagInhaltAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1);

            nachrichtenzumTagInhaltAdapter.addAll(newsDerWoche);
            GridView nachrichtenzumTagInhalt = view.findViewById(R.id.NachrichtenzumTagInhalt);
            nachrichtenzumTagInhalt.setAdapter(nachrichtenzumTagInhaltAdapter);
        }
    }

    private void startActivity() {
        //startActivity(new Intent(getContext(), MainActivityLoadscreenActivity.class));
    }


    @Override
    public void wochennummerLaden(boolean mitklassenauswahl, int klassenauswahl, int wochenauswahl, Integer wochennummer) {
        if (wochennummer == null) {
            startActivity();
        } else {
            nachrichtenDesTagesLadenTask = new NachrichtenDesTagesLadenTask(wochennummer, this);
            nachrichtenDesTagesLadenTask.execute();
        }
    }
}
