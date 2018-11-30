package de.hochrad.hochradapp.domain;

/*
Kein Raum heißt einfach, dass die klasse bzw. das Fach keinen Raum
für die Stunde zugeteilt bekommen hat.
 */

public class kein_Raum implements Raum {
    public kein_Raum() {
    }
    public String toString() {
        return "kein Raum";
    }
}
