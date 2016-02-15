package de.hochrad.hochradapp.domain;

/*
Die Art (der Vertretung) ist ein Wert, der den Schülern signalisiert,
um was für eine Art der Vertretung es sich handelt. Die Art kann z.B.
Entfall, Vertretung, Raumänderung, etc. sein. Es handelt sich bei der
Art im Grunde genommen um eine Zusammenfassung von dem, was in der
Vertretung steht.
 */

public class Art {
    public Art(String bezeichnung) {
        Bezeichnung = bezeichnung;
    }
    public String Bezeichnung;

    public String toString() {
        return Bezeichnung;
    }
}
