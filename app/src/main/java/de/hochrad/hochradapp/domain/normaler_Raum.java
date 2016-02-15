package de.hochrad.hochradapp.domain;

/*
Ein normaler Raum setzt sich aus einem Buchstaben und einer Zahl
zusammen. Der Buchstabe ist für den Bereich, in dem der Raum in
der Schule zu finden ist. Die Zahl ist dann zur feineren
Bestimmung für die Räume aus dem Bereich gedacht.
 */


public class normaler_Raum implements Raum{
    public normaler_Raum(String bereich, Integer nummer) {
        Bereich = bereich;
        Nummer = nummer;
    }

    public String Bereich;
    public Integer Nummer;

    public String toString (){
        return Bereich + Nummer.toString();
    }
}
