package com.spectron.dragoesdeshangrila.activities;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Objects;

public class MatchActivity extends PlayActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String matchId = (String) Objects.requireNonNull(getIntent().getExtras()).get("matchId");
        Toast.makeText(this, matchId, Toast.LENGTH_LONG).show();

        super.init("Ninguem", "Menos");
    }

    @Override
    protected void onEndDragons() {

    }

}
