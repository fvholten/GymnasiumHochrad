package de.hochrad.hochradapp.activities.loadscreen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import de.hochrad.hochradapp.R;

public class LoadscreenFragment extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Button retry = findViewById(R.id.retry);
        retry.setOnClickListener(v -> startActivity(newIntent(LoadscreenFragment.this)));
    }

    public Intent newIntent(LoadscreenFragment activity) {
        return null;
    }

}
