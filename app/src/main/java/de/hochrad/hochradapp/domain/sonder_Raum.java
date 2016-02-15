package de.hochrad.hochradapp.domain;

/*
Ein Sonderraum ist alles was ein normaler Raum nicht ist.
 */
public class sonder_Raum implements Raum{
    public sonder_Raum(String bezeichung) {
        Bezeichung = bezeichung;
    }

    public String Bezeichung;

    public String toString (){
        return Bezeichung;
    }
}
