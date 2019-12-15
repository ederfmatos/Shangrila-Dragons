package com.spectron.dragoesdeshangrila.activities;

import android.os.Bundle;
import android.view.View;

import com.spectron.dragoesdeshangrila.R;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MultiPlayerActivity extends PlayActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> players = Arrays.asList((String[]) Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("players")));

        super.init(players.get(0), players.get(1));
    }

    @Override
    public void onClickButtonRemove(View view) {
        if(super.hasSelected()) {
            return;
        }

        super.onClickButtonRemove(view);
        super.removeDragons();
        super.players.changePlayer();
    }

    @Override
    protected void onEndDragons() {
        super.startWinnerMusic();
        showEndMessage(players.getCurrentPlayer() + " " + getString(R.string.venceu));
    }

}
