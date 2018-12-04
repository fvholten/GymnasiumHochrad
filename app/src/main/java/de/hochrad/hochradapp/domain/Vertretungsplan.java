package de.hochrad.hochradapp.domain;

import java.util.ArrayList;
import java.util.List;
/*
//TODO
 */

public class Vertretungsplan {
//Da ich den Vertretungsplan nur Klassenweise laden, ergibt sich eine globale klasse
    public Klasse klasse;

    public List<Vertretung> vertretungen = new ArrayList<>();

    public void Hinzufügen(Vertretung neuesElement) {
        vertretungen.add(neuesElement);
    }
}
