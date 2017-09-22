package de.hochrad.hochradapp.activites;

import android.content.Context;
import android.content.Intent;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.einstellungen.EinstellungenActivity;
import de.hochrad.hochradapp.activites.startseite.MainActivity;
import de.hochrad.hochradapp.activites.stundenzeiten.StundenzeitenActivity;
import de.hochrad.hochradapp.activites.ueber.UeberActivity;
import de.hochrad.hochradapp.activites.vertretungsplan.VertretungsplanActivity;

public class NavigationDrawerNavigate {

    public NavigationDrawerNavigate(int id, Context context) {
        if (id == R.id.startseite && !context.toString().contains("startseite")) {
            MainAcitivyNavigate(context);
        } else if (id == R.id.vertretungsplan && !context.toString().contains("vertretungsplan")) {
            VertretungsplanActivityNavigate(context);
        } else if (id == R.id.einstellungen && !context.toString().contains("einstellungen")) {
            EinstellungsAcitivyNavigate(context);
        } else if (id == R.id.stundenzeiten && !context.toString().contains("stundenzeiten")) {
            StundenzeitenAcitivyNavigate(context);
        } else if (id == R.id.ueber && !context.toString().contains("ueber")) {
            UeberAcitivyNavigate(context);
        }
    }

    private void MainAcitivyNavigate(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    private void VertretungsplanActivityNavigate(Context context) {
        context.startActivity(new Intent(context, VertretungsplanActivity.class));
    }

    private void EinstellungsAcitivyNavigate(Context context) {
        context.startActivity(new Intent(context, EinstellungenActivity.class));
    }

    private void StundenzeitenAcitivyNavigate(Context context) {
        context.startActivity(new Intent(context, StundenzeitenActivity.class));
    }

    private void UeberAcitivyNavigate(Context context) {
        context.startActivity(new Intent(context, UeberActivity.class));
    }
}
