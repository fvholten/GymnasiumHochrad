package de.hochrad.hochradapp.domain;

import java.util.ArrayList;
import java.util.List;

import de.hochrad.hochradapp.domain.Vertretung;
/*
//TODO
 */

public class Vertretungsplan {
//Da ich den Vertretungsplan nur Klassenweise laden, ergibt sich eine globale klasse
    public Klasse klasse;

    public List<Vertretung> Vertretungen = new ArrayList<>();

    public void Hinzufügen(Vertretung neuesElement) {
        Vertretungen.add(neuesElement);
    }
}
