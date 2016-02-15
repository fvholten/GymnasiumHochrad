package de.hochrad.hochradapp.activites.vertretungsplan;

import android.content.Intent;

import de.hochrad.hochradapp.activites.LoadscreenActivity;

public class VertretungsplanActivityLoadscreenActivity extends LoadscreenActivity
{
    public VertretungsplanActivityLoadscreenActivity(){}

    public Intent newIntent(LoadscreenActivity activity)
    {
        return new Intent(activity, VertretungsplanActivity.class);
    }
}
