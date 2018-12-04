package de.hochrad.hochradapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.hochrad.hochradapp.BuildConfig;
import de.hochrad.hochradapp.R;

import java.util.Objects;

public class AboutFragment extends Fragment {
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ueber, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((FragmentActivity) Objects.requireNonNull(getActivity())).setTitle(R.string.about_text);
        ((TextView) view.findViewById(R.id.recht_text_view)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) view.findViewById(R.id.version_text_view)).setText(String.format("Version: %s", BuildConfig.VERSION_NAME));
    }
}
