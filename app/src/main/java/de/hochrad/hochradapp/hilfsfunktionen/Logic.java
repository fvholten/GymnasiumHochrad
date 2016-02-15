package de.hochrad.hochradapp.hilfsfunktionen;


import java.util.Calendar;
import java.util.Locale;

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
        if (i < 9) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    public static Boolean Wochenende() {
        Calendar calendar = Calendar.getInstance(Locale.GERMAN);
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        return (dayofweek == 1 || dayofweek == 7);
    }

    public static Integer GibStunde() {
        Calendar calendar = Calendar.getInstance(Locale.GERMAN);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static Integer GibMinute() {
        Calendar calendar = Calendar.getInstance(Locale.GERMAN);
        return calendar.get(Calendar.MINUTE);
    }
}
