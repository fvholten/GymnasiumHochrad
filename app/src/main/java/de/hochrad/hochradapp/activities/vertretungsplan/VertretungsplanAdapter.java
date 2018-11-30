package de.hochrad.hochradapp.activities.vertretungsplan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.domain.Vertretung;
import de.hochrad.hochradapp.domain.Vertretungsplan;


public class VertretungsplanAdapter extends RecyclerView.Adapter<VertretungsplanAdapter.ViewHolder> {


    private Vertretungsplan vertretungsplan;
    public LayoutInflater inflater;


    public VertretungsplanAdapter(Context context, Vertretungsplan vertretungsplan) {
        inflater = LayoutInflater.from(context);
        this.vertretungsplan = vertretungsplan;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Vertretung v = vertretungsplan.Vertretungen.get(position);

        holder.Wochentag.setText(v.Wochentag.toString());
        holder.Stunde.setText(v.Stunde.toString());
        holder.Fach.setText(v.Fach.toString());
        holder.Raumbezeichnung.setText(v.Raum.toString());
        //holder.stattFach.setText(v.statt_Fach.toString());
        //holder.stattRaum.setText(v.statt_Raum.toString());
        holder.Art.setText(v.Art.toString());
        holder.Informationen.setText(v.Informationen.toString());
    }

    @Override
    public int getItemCount() {
        return vertretungsplan.Vertretungen.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Stunde, Art, Fach, Raumbezeichnung, Informationen, Wochentag;

        public ViewHolder(View itemView) {
            super(itemView);

            Stunde = (TextView) itemView.findViewById(R.id.Stunde);
            Art = (TextView) itemView.findViewById(R.id.Art);
            Fach = (TextView) itemView.findViewById(R.id.Fach);
            Raumbezeichnung = (TextView) itemView.findViewById(R.id.Raumbezeichnung);
            //stattFach = (TextView) itemView.findViewById(R.id.stattFach);
            //stattRaum = (TextView) itemView.findViewById(R.id.stattRaum);
            Informationen = (TextView) itemView.findViewById(R.id.Informationen);
            Wochentag = (TextView) itemView.findViewById(R.id.Wochentag);
        }
    }
}
