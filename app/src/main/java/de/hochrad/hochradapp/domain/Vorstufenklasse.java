package de.hochrad.hochradapp.domain;

/*
Die Vorstufenklasse ist eine klasse, die sich aus einem V, was für Vorstufe
zusammen steht und einem weiterem Buchstaben, der die Vorstufe in
Klassen unterteilt.
 */

public class Vorstufenklasse implements Klasse{
//TODO
    public Vorstufenklasse(String aufteilung) {
        Aufteilung = aufteilung;
    }
    public String Aufteilung;

    public String toString (){
        return "V" + Aufteilung;
    }
}
