package de.hochrad.hochradapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.hochrad.hochradapp.R;

public class StundenzeitenFragment extends Fragment {

    public StundenzeitenFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stundenzeiten, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ListView) view.findViewById(R.id.school_hours)).setAdapter(new ArrayAdapter<>(
                view.getContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.school_hours)));
    }
}
