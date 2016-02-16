package de.hochrad.hochradapp.hilfsfunktionen;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Optionen {

    public Optionen(Context context, String klassenauswahl) {
        this.context = context;
        ladeDaten(klassenauswahl);
    }

    private Context context;
    private int daten;

    public int getFile() {
        return daten;
    }

    public void putFile(int neuerWert, String filename) {
        speichereDaten(neuerWert, filename);
        daten = neuerWert;
    }

    public void ladeDaten(String file) {
        try {
            FileInputStream fileInputStream = context.openFileInput(file);
            BufferedReader r = new BufferedReader(new InputStreamReader(fileInputStream));
            daten = r.read();
        } catch (IOException e) {
            daten = 1;
        }
    }

    private void speichereDaten(int neuerWert, String file) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(file, Context.MODE_PRIVATE);
            Writer out = new OutputStreamWriter(fileOutputStream);
            out.write(neuerWert);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}