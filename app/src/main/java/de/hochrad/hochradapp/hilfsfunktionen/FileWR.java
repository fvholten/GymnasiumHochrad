package de.hochrad.hochradapp.hilfsfunktionen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWR {

    public int ladeFile(String file) {
        try {
            DataInputStream dataIn = new DataInputStream(new FileInputStream(file + ".txt"));
            return dataIn.readInt();
        } catch (IOException e) {
            return 1;
        }
    }

    public void writeFile(int neuerWert, String filename) {
        try {
            DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(filename + ".txt"));
            dataOut.writeInt(neuerWert);
            dataOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}