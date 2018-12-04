package de.hochrad.hochradapp.loader;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.List;

import de.hochrad.hochradapp.domain.Klasse;
import de.hochrad.hochradapp.domain.Oberstufenklasse;
import de.hochrad.hochradapp.domain.Vertretung;
import de.hochrad.hochradapp.domain.Art;
import de.hochrad.hochradapp.domain.andere_Klasse;
import de.hochrad.hochradapp.domain.Fach;
import de.hochrad.hochradapp.domain.Information;
import de.hochrad.hochradapp.domain.Vertretungsplan;
import de.hochrad.hochradapp.domain.Vorstufenklasse;
import de.hochrad.hochradapp.domain.kein_Raum;
import de.hochrad.hochradapp.domain.nicht_angegebener_Raum;
import de.hochrad.hochradapp.domain.normale_Klasse;
import de.hochrad.hochradapp.domain.normaler_Raum;
import de.hochrad.hochradapp.domain.Raum;
import de.hochrad.hochradapp.domain.sonder_Raum;
import de.hochrad.hochradapp.domain.Stunde;
import de.hochrad.hochradapp.domain.Wochentag;

// Allerlei Hilfsfunktionen für Wochentag

public class ParseUtilities {

    // Wandelt einen String in den entsprechenden Wochentag um.

    public static Wochentag ToWochentag(String text) throws IllegalArgumentException {
        if (text.contains("Montag")) return Wochentag.Montag;
        if (text.contains("Dienstag")) return Wochentag.Dienstag;
        if (text.contains("Mittwoch")) return Wochentag.Mittwoch;
        if (text.contains("Donnerstag")) return Wochentag.Donnerstag;
        if (text.contains("Freitag")) return Wochentag.Freitag;
        throw new IllegalArgumentException(text + " kann nicht in einen Wochentag umgewandelt werden");
    }

    // Wandelt einen String in den entsprechenden Raum um.

    public static Raum ToRaum(String text) {
        if (text.equals("---") || text.equals("???")) return new nicht_angegebener_Raum();
        if (text.length() == 1 && text.charAt(0) == (char) 160) return new kein_Raum();
        String[] splitted = text.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        if (splitted.length != 2) return new sonder_Raum(text);
        try {
            return new normaler_Raum(splitted[0], Integer.parseInt(splitted[1]));
        } catch (NumberFormatException e) {
            return new sonder_Raum(text);
        }
    }

    // Wandelt einen String in das entsprechende Fach um.

    public static Fach ToFach(String text) {
        return new Fach(text);
    }

    // Wandelt einen String in die entsprechende Stunde um.
    public static Stunde ToStunde(String text) {
        return new Stunde(text);
    }

    // Wandelt einen String in die entsprechende Art (der Vertretung) um.

    public static Art ToArt(String text) {
        return new Art(text);
    }

    // Wandelt einen String in die entsprechende Information um.

    public static Information ToInformation(String text) {
        return new Information(text);
    }

    public static Klasse ToKlasse(String text) {

        if (text.equals("3./4. Semester")) {
            return new Oberstufenklasse(3);
        }
        if (text.equals("1./2. Semester")) {
            return new Oberstufenklasse(1);
        }
        if (text.startsWith("klasse V")) {
            return new Vorstufenklasse(text.substring(8));
        }
        if (text.startsWith("klasse")) {
            return new normale_Klasse(Integer.parseInt(text.substring(7, 8)), text.substring(8));
        } else {
            return new andere_Klasse(text);
        }
    }

    /*
    Ein Vertretungsplan ist ein Plan auf dem Stundenplanveränderungen angezeigt werden. Eine
    Stunde kann ausfallen, vertreten werden oder verlegt werden. Außerdem kann sich der Raum
    oder das Fach verändern.
    Wenn ein Lehrer nicht zur Stunde erscheinen kann wird entschieden, ob die Stunde vertreten
    wird oder ob sie ausfällt. Wenn sie Vertreten wird so macht das ein Vertretungslehrer.
    Ein Vertretungsplan setzt sich aus vertretungen zusammen.
     */

    public static void ParseVertretungsplan(Document doc, Vertretungsplan vertretungsplan) {
        // Aufbau der Seite (Beispielhaft)
        // <div id="vertretung">
        //  ... <b>18.5. Montag</b> ...
        //  <table class="subst" > ***Details für Montag*** </table>
        //  ... <b>20.5. Dienstag</b> ...
        //  <table class="subst" > ***Details für Dienstag*** </table>
        //  ...
        // </div>

        Element h2 = doc.select("h2").get(0);
        vertretungsplan.klasse = ParseUtilities.ToKlasse(h2.text());

        Element outerDiv = doc.getElementById("vertretung");
        String wochentag = "";
        if (outerDiv != null) {
            for (Element part : outerDiv.children()) {
                if (part.nodeName().equals("b") && !part.text().startsWith("[")) {
                    wochentag = part.text();
                } else if (part.nodeName().equals("p")) {
                    for (Element part2 : part.children()) {
                        if (part2.nodeName().equals("b") && !part2.text().startsWith("[")) {
                            wochentag = part2.text();
                        }
                    }
                } else if (part.nodeName().equals("table")) {
                    if (part.attributes().hasKey("class") && part.attributes().get("class").equals("subst")) {
                        Element[] elemente0 = part.select("tr").toArray(new Element[0]);
                        for (int i = 1; i < elemente0.length; i++) {
                            Element[] tds = elemente0[i].select("td").toArray(new Element[0]);
                            if (tds.length >= 7) {
                                Vertretung vertretung = new Vertretung();
                                try {
                                    vertretung.Wochentag = ParseUtilities.ToWochentag(wochentag);
                                    vertretung.Stunde = ParseUtilities.ToStunde(tds[0].text());
                                    vertretung.Fach = ParseUtilities.ToFach(tds[2].text());
                                    vertretung.Raum = ParseUtilities.ToRaum(tds[3].text());
                                    vertretung.Art = ParseUtilities.ToArt(tds[4].text());
                                    vertretung.Informationen = ParseUtilities.ToInformation(tds[7].text());
                                    vertretung.Klasse = vertretungsplan.klasse;
                                    vertretungsplan.Hinzufügen(vertretung);
                                } catch (IllegalArgumentException e) {
                                    vertretungsplan = null;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*
    Die Nachrichten des Tages sind wichtige Informationen, die alle Schüler mitbekommen sollen.
    Sie sind zentral verfügbar und für alle Klassen gleich. Sie sind ein Element des
    Vertretungsplanes.
     */

    public static List<String> ParseNachrichtenDesTages(Document doc) {
        ArrayList<String> NachrichtenzumTag = new ArrayList<>();
        for (Element element : doc.select("table")) {
            for (Node childNode : element.childNodes()) {
                if (childNode instanceof Element) {
                    Element tbody = (Element) childNode;
                    if (tbody.tag().getName().equals("tbody") && tbody.childNodes().size() > 0) {
                        Node childNode2 = tbody.childNodes().get(0);
                        if (childNode2 instanceof Element) {
                            Element tr = (Element) childNode2;
                            if (tr.tag().getName().equals("tr") && tr.childNodes().size() > 0) {
                                Node childNode3 = tr.childNodes().get(0);
                                if (childNode3 instanceof Element) {
                                    Element td = (Element) childNode3;
                                    if (td.text().equals("Nachrichten zum Tag")) {
                                        for (int i = 1; i < tbody.childNodes().size(); i++) {
                                            Node inhalt = tbody.childNodes().get(i);
                                            if (inhalt instanceof Element) {
                                                Element b = (Element) inhalt;
                                                NachrichtenzumTag.add(b.text());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (NachrichtenzumTag == null) {
            return null;
        } else {
            return NachrichtenzumTag;

        }
    }

    /*
    Eine klasse ist ein Zusammenschluss von Schülern, die denselben Unterricht besuchen und
    somit dieselben Lehrer, Stundepläne und Vertretungspläne haben. Die klasse wird in den
    gleichen Fächern, Räumen und Stunden unterrichtet. Jede klasse hat eine Inhalt.
    Zunächst in welchem Schuljahr die klasse ist und dann ein Buchstabe von a anfangend.
    Es gibt die normale klasse, Oberstufenklasse und Vorstufenklasse.
     */

    public static String[] ParseKlassenEinlesen(Document doc) {
        String text = doc.toString();
        String[] splitted = text.split("var classes = \\[\"");
        if (splitted.length == 2) {
            splitted = splitted[1].split("\"];");
            if (splitted.length > 0) {
                if (splitted[0].split("\",\"") == null) {
                    return null;
                } else {
                    return splitted[0].split("\",\"");
                }
            }
        }
        return null;
    }

    //hier werden die Wochen aus der Webseite ausgelesen

    public static String[] ParseWochenEinlesen(Document doc) {
        for (Element element : doc.select("table")) {
            for (Node childNode : element.childNodes()) {
                if (childNode instanceof Element) {
                    Element tbody = (Element) childNode;
                    if (tbody.children().get(0).children().get(0).children().get(0).getElementsByTag("select").get(0).children().text().split(" ") == null) {
                        return null;
                    } else {
                        return tbody.children().get(0).children().get(0).children().get(0).getElementsByTag("select").get(0).children().text().split(" ");
                    }
                }
            }
        }
        return null;
    }

    /*
    Die Wochennummer oder auch die Woche des Jahres seht für jeweils eine Woche. Mit jeder
    voranschreitenden Woche steigt sie um einen. Der Vertretungsplan in sie unterteilt und
    nach ihnen aufgebaut.
     */

    public static String ParseWochennummerEinlesen(Document doc) {
        for (Element element : doc.select("table")) {
            for (Node childNode : element.childNodes()) {
                if (childNode instanceof Element) {
                    Element tbody = (Element) childNode;
                    if (tbody.children().get(0).children().get(0).children().get(0).getElementsByTag("select").get(0).children().val() == null) {
                        return null;
                    } else {
                        return tbody.children().get(0).children().get(0).children().get(0).getElementsByTag("select").get(0).children().val();
                    }
                }
            }
        }
        return null;
    }

//    TODO
//    public static void ParseWochenplanEinlesen(Document doc) {
//
//        for (Element element : doc.select("td")) {
//            if (!(element.childNode(1) instanceof Element)) {
//                String ü1 = element.childNode(1).toString();
//            }
//            for (Node childNodes : element.childNodes()) {
//                if (childNodes instanceof Element)
//                    for (Node h : element.childNodes()) {
//                        if (h instanceof Element) {
//                            h.childNodes();
//                        }
//                    }
//            }
//        }
//    }
}
