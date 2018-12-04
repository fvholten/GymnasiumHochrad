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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activities.substitution.DetailKlassenauswahlActivity;
import de.hochrad.hochradapp.activities.substitution.DetailSubstitutionScheduleActivity;
import de.hochrad.hochradapp.loader.WochenLadenTask;
import de.hochrad.hochradapp.loader.WochenLadenTaskCallBack;
import de.hochrad.hochradapp.loader.WochennummerLadenTask;
import de.hochrad.hochradapp.loader.WochennummerLadenTaskCallBack;

import java.util.Objects;

public class SubstitutionScheduleFragment extends Fragment implements WochenLadenTaskCallBack, WochennummerLadenTaskCallBack {
    private Context context;
    private DetailView detailView;
    private View view;
    private WochenLadenTask wochenLadenTask;
    private WochennummerLadenTask wochennummerLadenTask;

    public interface DetailView {
        void openDetails(Class cls, Bundle bundle);
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.wochenLadenTask != null) {
            this.wochenLadenTask.cancel(true);
        }
        if (this.wochennummerLadenTask != null) {
            this.wochennummerLadenTask.cancel(true);
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_substitution_schedule, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle(R.string.substitution_schedule);
        this.view = view;
        this.context = getContext();
        this.wochenLadenTask = new WochenLadenTask(this);
        this.wochenLadenTask.execute();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.detailView = (DetailView) context;
    }

    private ArrayAdapter<String> getWeekDropdownAdapter(String[] weeks) {
        ArrayAdapter<String> weekDropdownAdapter = new ArrayAdapter<>(this.context, android.R.layout.simple_spinner_dropdown_item);
        weekDropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekDropdownAdapter.add(getString(R.string.choose_a_week));
        weekDropdownAdapter.addAll(weeks);
        return weekDropdownAdapter;
    }

    public void attachWeeks(String[] weeks) {
        if (weeks != null) {
            Spinner yourClass = this.view.findViewById(R.id.deineKlasse);
            yourClass.setAdapter(getWeekDropdownAdapter(weeks));
            yourClass.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        wochennummerLadenTask = new WochennummerLadenTask(false, 0, position - 1, SubstitutionScheduleFragment.this);
                        wochennummerLadenTask.execute();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            Spinner week = this.view.findViewById(R.id.week);
            week.setAdapter(getWeekDropdownAdapter(weeks));
            week.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(SubstitutionScheduleFragment.this.getString(R.string.selected_item_key), position);
                        SubstitutionScheduleFragment.this.detailView.openDetails(DetailKlassenauswahlActivity.class, bundle);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public void loadWeekNumber(boolean mitklassenauswahl, int klassenauswahl, int wochenauswahl, Integer wochennummer) {
        if (wochennummer == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.mitklassenauswahl_key), mitklassenauswahl);
        bundle.putInt(getString(R.string.klassenauswahl_key), klassenauswahl);
        bundle.putInt(getString(R.string.wochenauswahl_key), wochenauswahl);
        bundle.putInt(getString(R.string.wochennummer_key), wochennummer.intValue());
        this.detailView.openDetails(DetailSubstitutionScheduleActivity.class, bundle);
    }
}
