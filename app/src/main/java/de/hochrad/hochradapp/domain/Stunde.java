package de.hochrad.hochradapp.domain;

/*
Eine Stunde ist eine Unterrichtseinheit von 45 min. Die erste Stunde beginnt
um 7:50 Uhr. Der Schüler und Lehrer richtet sich nach ihnen. Der Stundenplan
und Vertretungsplan ist in Stunden aufgeteilt.
 */

public class Stunde {
    public Stunde (String bezeichnung) {
        Zeitangabe = bezeichnung;
    }
    public String Zeitangabe;

    public String toString (){
        return Zeitangabe;
    }
}
