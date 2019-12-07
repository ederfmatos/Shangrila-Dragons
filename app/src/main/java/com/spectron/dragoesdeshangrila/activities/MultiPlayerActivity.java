package com.spectron.dragoesdeshangrila.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MultiPlayerActivity extends PlayActivity {

    private List<String> players;
    private boolean isChanceFirstPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.init();
        players = Arrays.asList((String[]) Objects.requireNonNull(getIntent().getExtras().get("players")));
        isChanceFirstPlayer = true;
    }

    @Override
    public void onClickButtonRemove(View view) {
        isChanceFirstPlayer = !isChanceFirstPlayer;
        removeDragons();
    }

    @Override
    protected void onEndDragons() {
        String winner = players.get(isChanceFirstPlayer ? 1 : 0);
        Toast.makeText(this, winner + " venceu!", Toast.LENGTH_LONG).show();
    }

}
