package de.hochrad.hochradapp.hilfsfunktionen;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.concurrent.ExecutionException;

public class Optionen {

    public Optionen(Context context, String klassenauswahl) {
        myContext = context;
        ladeDaten(klassenauswahl);
    }

    private Context myContext;
    private Integer teilseite = 1;

    public Integer getTeilseite() {
        return teilseite;
    }

    public void putTeilseite(Integer neuerWert, String filename) {
        speichereDaten(neuerWert, filename);
        teilseite = neuerWert;
    }

    public void ladeDaten(String file) {
        try {
            FileInputStream fileInputStream = myContext.openFileInput(file);
            BufferedReader r = new BufferedReader(new InputStreamReader(fileInputStream));
            teilseite = r.read();
        } catch (IOException e) {
            teilseite = 1;
        }
    }

    private void speichereDaten(Integer neuerWert, String file) {
        try {
            FileOutputStream fileOutputStream = myContext.openFileOutput(file, Context.MODE_PRIVATE);
            Writer out = new OutputStreamWriter(fileOutputStream);
            out.write(neuerWert);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}