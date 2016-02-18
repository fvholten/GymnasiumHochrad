package de.hochrad.hochradapp.activites.einstellungen;

import android.content.Intent;

import de.hochrad.hochradapp.activites.loadscreen.LoadscreenActivity;
import de.hochrad.hochradapp.activites.vertretungsplan.VertretungsplanActivity;

public class EinstellungenActivityLoadscreenActivity extends LoadscreenActivity
{
    public EinstellungenActivityLoadscreenActivity(){}

    public Intent newIntent(LoadscreenActivity activity)
    {
        return new Intent(activity, VertretungsplanActivity.class);
    }
}

