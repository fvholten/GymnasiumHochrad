package de.hochrad.hochradapp.domain;

/*
Ein Fach wird zu einer bestimmten Stunde, in einem bestimmten Raum,
für eine klasse unterrichtet. Das Fach ist eine Inhalt, die
 beschreibt, was stattfindet. Die Lehrer geben die Fächer die Schüler
 nehmen teil.
 */

public class Fach {
    public Fach(String bezeichnung) {
        Bezeichnung = bezeichnung;
    }
    public String Bezeichnung;

    public String toString() {
        return Bezeichnung;
    }
}
