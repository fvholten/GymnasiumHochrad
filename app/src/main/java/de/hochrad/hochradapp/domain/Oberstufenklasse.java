package de.hochrad.hochradapp.domain;

/*
Die Oberstufenklasse ist eine klasse, sie setzt sich aus dem Buchstaben S, was für Semester steht,
einer Zahl, die das Semester unterteilt, einem Sonderzeichen, einem weiterem S, wieder
für Semester und einer weiteren Zahl, die wieder für die Unterteilung der Semester steht.
Bei der Oberstufenklasse handelt es sich um die Oberstufe, welche in 4 Semester aufgeteilt wurde.
 */

public class Oberstufenklasse implements Klasse {

    public Oberstufenklasse(Integer semesternummer) {
        Semesternummer = semesternummer;
    }

    public Integer Semesternummer;

    public String toString() {
        return "S" + Semesternummer.toString() + "/" + "S" + Integer.valueOf(Semesternummer +1).toString();
    }
}
