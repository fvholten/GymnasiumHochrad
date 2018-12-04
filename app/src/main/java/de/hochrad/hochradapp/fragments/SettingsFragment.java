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
import android.widget.AdapterView.OnItemSelectedListener;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.hilfsfunktionen.FileWR;
import de.hochrad.hochradapp.loader.KlassenLadenTask;
import de.hochrad.hochradapp.loader.KlassenLadenTaskCallBack;
import de.hochrad.hochradapp.service.Service;

import java.io.File;
import java.util.Objects;

public class SettingsFragment extends Fragment implements KlassenLadenTaskCallBack {
    private TextView auswahl;
    private FileWR fileWR;
    private Spinner klassenspinner;
    private View view;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @SuppressLint({"DefaultLocale"})
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle(R.string.settings_text);
        this.view = view;
        this.fileWR = new FileWR();
        String s = Objects.requireNonNull(getContext()).getFilesDir() + File.separator + "switch";
        int switcher = fileWR.ladeFile(s);

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

        editText.setText(String.format("%d", this.fileWR.ladeFile(String.format("%s%sservicezeit", getContext().getFilesDir(), File.separator))));

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isValidInput(s.toString())) {
                    Toast.makeText(SettingsFragment.this.getContext(), "Gespeichert!", Toast.LENGTH_SHORT).show();
                    FileWR access$100 = SettingsFragment.this.fileWR;
                    int parseInt = Integer.parseInt(s.toString());
                    String stringBuilder = Objects.requireNonNull(SettingsFragment.this.getContext()).getFilesDir() +
                            File.separator +
                            "servicezeit";
                    access$100.writeFile(parseInt, stringBuilder);
                    SettingsFragment.this.getContext().startService(new Intent(SettingsFragment.this.getContext(), Service.class));
                    return;
                }
                Toast.makeText(SettingsFragment.this.getContext(), "Not valid!", Toast.LENGTH_SHORT).show();
            }

        });
        notification_switch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            String stringBuilder1 = Objects.requireNonNull(getContext()).getFilesDir() +
                                    File.separator +
                                    "switch";
                            fileWR.writeFile(2, stringBuilder1);
                            linearLayout.setVisibility(View.VISIBLE);
                            Objects.requireNonNull(getContext()).startService(new Intent(getContext(), Service.class));
                            return;
                        }
                        String stringBuilder = Objects.requireNonNull(getContext()).getFilesDir() +
                                File.separator +
                                "switch";
                        fileWR.writeFile(1, stringBuilder);
                        linearLayout.setVisibility(View.INVISIBLE);
                        getContext().startService(new Intent(getContext(), Service.class));
                    }
                }
        );

        new KlassenLadenTask(0, this).execute();
    }

    private boolean isValidInput(String input) {
        return !input.isEmpty() && !input.contains(",") && !input.contains(".");
    }

    public void klassenLaden(int wochenauswahl, String[] klassen) {
        if (klassen != null) {
            this.fileWR = new FileWR();
            this.view.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            ArrayAdapter<String> klassenauswahlAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item);
            klassenauswahlAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            klassenauswahlAdapter.add("Wähle deine Klasse");
            klassenauswahlAdapter.addAll(klassen);
            this.klassenspinner = this.view.findViewById(R.id.klassenauswahlspinner);
            this.klassenspinner.setAdapter(klassenauswahlAdapter);
            this.auswahl = this.view.findViewById(R.id.wahl);
            TextView textView = this.auswahl;
            Object[] objArr = new Object[2];
            objArr[0] = getString(R.string.aktuelleÜberwachung);
            objArr[1] = this.klassenspinner.getItemAtPosition(this.fileWR.ladeFile(String.format("%s%sauswahl", getContext().getFilesDir(), File.separator))).toString();
            textView.setText(String.format("%s %s", objArr));
            this.klassenspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        Toast.makeText(SettingsFragment.this.getContext(), "Gespeichert.", Toast.LENGTH_SHORT).show();
                        FileWR access$100 = SettingsFragment.this.fileWR;
                        String stringBuilder = Objects.requireNonNull(SettingsFragment.this.getContext()).getFilesDir() +
                                File.separator +
                                "auswahl";
                        access$100.writeFile(position, stringBuilder);
                        TextView access$300 = SettingsFragment.this.auswahl;
                        Object[] objArr = new Object[2];
                        objArr[0] = SettingsFragment.this.getString(R.string.Einstellungengespeichert);
                        Spinner access$200 = SettingsFragment.this.klassenspinner;
                        FileWR access$1002 = SettingsFragment.this.fileWR;
                        String stringBuilder2 = SettingsFragment.this.getContext().getFilesDir() +
                                File.separator +
                                "auswahl";
                        objArr[1] = access$200.getItemAtPosition(access$1002.ladeFile(stringBuilder2)).toString();
                        access$300.setText(String.format("%s%s", objArr));

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
