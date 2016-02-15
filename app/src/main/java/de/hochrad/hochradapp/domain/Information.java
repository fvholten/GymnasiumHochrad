package de.hochrad.hochradapp.domain;
/*
Die Information ist eine Zusatzinformation zur Vertretung.
Der Lehrer kann hier mit den Schülern kommunizieren und
ihnen z.B. eine Aufgabe und anderes zu übermitteln.
 */
public class Information {
    public Information(String inhalt) {
        Inhalt = inhalt;
    }
    public String Inhalt;

    public String toString() {
        return Inhalt;
    }
}
