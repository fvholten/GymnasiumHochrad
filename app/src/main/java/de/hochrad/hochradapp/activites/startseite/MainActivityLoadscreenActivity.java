package de.hochrad.hochradapp.activites.startseite;

import android.content.Intent;

import de.hochrad.hochradapp.activites.LoadscreenActivity;

public class MainActivityLoadscreenActivity extends LoadscreenActivity
{
    public MainActivityLoadscreenActivity(){}

    public Intent newIntent(LoadscreenActivity activity)
    {
        return new Intent(activity, MainActivity.class);
    }
}
