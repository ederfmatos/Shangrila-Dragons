package com.spectron.dragoesdeshangrila.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.spectron.dragoesdeshangrila.R;

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
        setCurrentPlayer();
    }

    @Override
    public void onClickButtonRemove(View view) {
        removeDragons();
        isChanceFirstPlayer = !isChanceFirstPlayer;
        setCurrentPlayer();
    }

    @Override
    protected void onEndDragons() {
        Toast.makeText(this, getCurrentPlayer() + " " + getString(R.string.venceu), Toast.LENGTH_LONG).show();
    }

    private String getCurrentPlayer() {
        return players.get(isChanceFirstPlayer ? 0 : 1);
    }

    private void setCurrentPlayer() {
        setPlayerName(getString(R.string.vez_de) + getCurrentPlayer());
    }

}
