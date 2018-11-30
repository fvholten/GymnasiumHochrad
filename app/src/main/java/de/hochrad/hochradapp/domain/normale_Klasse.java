package de.hochrad.hochradapp.domain;

/*
Die normale klasse ist eine klasse, sie setzt sich aus
der Stufe, also einer Zahl und der Aufteilung in der
Stufe, es handelt sich dabei um Buchstaben zusammen.
 */

public class normale_Klasse implements Klasse{
    public normale_Klasse(Integer stufe, String aufteilung) {
        Stufe = stufe;
        Aufteilung = aufteilung;
    }
    public Integer Stufe;
    public String Aufteilung;

    public String toString (){
        return Stufe.toString() + Aufteilung;
    }
}
