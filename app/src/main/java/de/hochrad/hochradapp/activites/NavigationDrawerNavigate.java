package de.hochrad.hochradapp.activites;

import android.content.Context;
import android.content.Intent;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.einstellungen.EinstellungenActivity;
import de.hochrad.hochradapp.activites.startseite.MainActivity;
import de.hochrad.hochradapp.activites.stundenzeiten.StundenzeitenActivity;
import de.hochrad.hochradapp.activites.ueber.UeberActivity;
import de.hochrad.hochradapp.activites.vertretungsplan.VertretungsplanActivity;
import de.hochrad.hochradapp.activites.wochenplan.WochenplanActivtiy;

public class NavigationDrawerNavigate {

    public NavigationDrawerNavigate(int id, Context context) {
        if (id == R.id.startseite && !context.toString().contains("startseite")) {
            MainAcitivyNavigate(context);
        } else if (id == R.id.vertretungsplan && !context.toString().contains("vertretungsplan")) {
            VertretungsplanActivityNavigate(context);
        } else if (id == R.id.einstellungen && !context.toString().contains("einstellungen")) {
            EinstellungsAcitivyNavigate(context);
        } else if (id == R.id.wochenplan && !context.toString().contains("wochenplan")) {
            WochenplanAcitivyNavigate(context);
        } else if (id == R.id.stundenzeiten && !context.toString().contains("stundenzeiten")) {
            StundenzeitenAcitivyNavigate(context);
        } else if (id == R.id.ueber && !context.toString().contains("ueber")) {
            UeberAcitivyNavigate(context);
        }
    }

    public void MainAcitivyNavigate(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public void VertretungsplanActivityNavigate(Context context) {
        context.startActivity(new Intent(context, VertretungsplanActivity.class));
    }

    public void EinstellungsAcitivyNavigate(Context context) {
        context.startActivity(new Intent(context, EinstellungenActivity.class));
    }

    public void WochenplanAcitivyNavigate(Context context) {
        context.startActivity(new Intent(context, WochenplanActivtiy.class));
    }

    public void StundenzeitenAcitivyNavigate(Context context) {
        context.startActivity(new Intent(context, StundenzeitenActivity.class));
    }

    public void UeberAcitivyNavigate(Context context) {
        context.startActivity(new Intent(context, UeberActivity.class));
    }
}
