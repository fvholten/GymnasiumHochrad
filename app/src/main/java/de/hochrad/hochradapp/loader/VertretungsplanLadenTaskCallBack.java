package de.hochrad.hochradapp.loader;


import de.hochrad.hochradapp.domain.Vertretungsplan;

public interface VertretungsplanLadenTaskCallBack {
    void VertretungsplanLaden(int hash, Vertretungsplan vertretungsplan);
}
