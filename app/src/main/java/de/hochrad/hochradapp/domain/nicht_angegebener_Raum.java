package de.hochrad.hochradapp.domain;

/*
Ein nicht angegebenen Raum ist, ein Raum, der aus entweder
organisatorischen Gründen nicht angeben wurde oder aus
sachlichen Gründen nicht angeben werden muss. Auf der
Webseite wird er mit „---„ oder „???“ gekennzeichnet.
 */

public class nicht_angegebener_Raum implements Raum{

    public nicht_angegebener_Raum() {
    }

    public String toString (){
        return "unbekannt";
    }
}
