package de.hochrad.hochradapp.hilfsfunktionen;


public class Logic {

    public static Integer ToInteger(String wochennummer) {
        return Integer.parseInt(wochennummer);
    }

    public static String fiveDigits(int i)
    {
        return "000" + twoDigits(i);
    }
    public static String twoDigits(int i)
    {
        if (i <= 9) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }
}
