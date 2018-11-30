package de.hochrad.hochradapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.hilfsfunktionen.ConnectionTest;
import de.hochrad.hochradapp.hilfsfunktionen.FileWR;
import de.hochrad.hochradapp.loader.KlassenLadenTask;
import de.hochrad.hochradapp.loader.KlassenLadenTaskCallBack;
import de.hochrad.hochradapp.service.Service;

import java.io.File;
import java.util.Objects;

public class EinstellungenFragment extends Fragment implements KlassenLadenTaskCallBack {

    private TextView auswahl;
    private FileWR fileWR;
    private Spinner klassenspinner;
    private View view;

    public EinstellungenFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_einstellungen, container, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;

        fileWR = new FileWR();
        int switcher = fileWR.ladeFile(Objects.requireNonNull(getContext()).getFilesDir() + File.separator + "switch");
        Switch notification_switch = view.findViewById(R.id.switchNotifications);
        final LinearLayout linearLayout = view.findViewById(R.id.SwitchON);
        if (switcher == 2) {
            linearLayout.setVisibility(View.VISIBLE);
            notification_switch.setChecked(true);
        } else {
            notification_switch.setChecked(false);
            linearLayout.setVisibility(View.INVISIBLE);
        }
        EditText editText = view.findViewById(R.id.editNotification);
        editText.setText(String.format("%d", fileWR.ladeFile(String.format("%s%sservicezeit", getContext().getFilesDir(), File.separator))));
        editText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    Toast.makeText(getContext(), "Gespeichert!", Toast.LENGTH_SHORT).show();
                    fileWR.writeFile(Integer.parseInt(s.toString()), Objects.requireNonNull(getContext()).getFilesDir() + File.separator + "servicezeit");
                    getContext().startService(new Intent(getContext(), Service.class));
                }
            }
        });
        notification_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fileWR.writeFile(2, getContext().getFilesDir() + File.separator + "switch");
                linearLayout.setVisibility(View.VISIBLE);
                getContext().startService(new Intent(getContext(), Service.class));
            } else {
                fileWR.writeFile(1, getContext().getFilesDir() + File.separator + "switch");
                linearLayout.setVisibility(View.INVISIBLE);
                getContext().startService(new Intent(getContext(), Service.class));
            }
        });
        if (!ConnectionTest.isOnline(Objects.requireNonNull(getContext()))) {
            startActivity();
        }
        KlassenLadenTask klassenLadenTask = new KlassenLadenTask(0, this);
        klassenLadenTask.execute();

    }

    @Override
    public void klassenLaden(int wochenauswahl, String[] klassen) {
        if (klassen == null) {
            startActivity();
        } else {
            fileWR = new FileWR();
            ProgressBar progressBar = view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            ArrayAdapter<String> klassenauswahlAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item);
            klassenauswahlAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            klassenauswahlAdapter.add("Wähle deine klasse!");
            klassenauswahlAdapter.addAll(klassen);
            klassenspinner = view.findViewById(R.id.klassenauswahlspinner);
            klassenspinner.setAdapter(klassenauswahlAdapter);
            auswahl = view.findViewById(R.id.wahl);
            auswahl.setText(String.format("%s%s", getString(R.string.aktuelleÜberwachung), klassenspinner.getItemAtPosition(fileWR.ladeFile(String.format("%s%sauswahl", getContext().getFilesDir(), File.separator))).toString()));

            klassenspinner.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int positionklassenauswahl, long id) {
                            if (positionklassenauswahl == 0) {
                                return;
                            }
                            Toast.makeText(getContext(), "Gespeichert.", Toast.LENGTH_SHORT).show();
                            fileWR.writeFile(positionklassenauswahl, Objects.requireNonNull(getContext()).getFilesDir() + File.separator + "auswahl");
                            auswahl.setText(String.format("%s%s", getString(R.string.Einstellungengespeichert), klassenspinner.getItemAtPosition(fileWR.ladeFile(getContext().getFilesDir() + File.separator + "auswahl")).toString()));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
        }
    }

    private void startActivity() {
        //startActivity(new Intent(getContext(), EinstellungenActivityLoadscreenActivity.class));
    }

}
