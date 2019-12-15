package com.spectron.dragoesdeshangrila.activities;

import android.os.Bundle;
import android.view.View;

import com.spectron.dragoesdeshangrila.R;
import com.spectron.dragoesdeshangrila.enumerations.LevelEnum;

import java.util.Objects;

public class SinglePlayerActivity extends PlayActivity {

    private LevelEnum level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init(getString(R.string.voce), getString(R.string.maquina));

        level = (LevelEnum) Objects.requireNonNull(getIntent().getExtras()).get("level");
    }

    @Override
    public void onClickButtonRemove(View view) {
        if (!hasSelected() || !players.isFirstPlayer()) return;
        super.onClickButtonRemove(view);

        super.removeDragons();
        players.changePlayer();

        if (getQuantityDragonsVisible() > 0) {
            machineChance();
        }
    }

    @Override
    protected void onEndDragons() {
        if (players.isFirstPlayer()) {
            youWin();
        } else {
            youLose();
        }
    }

    private void machineChance() {
        wait(() -> {
            int quantity = level.getAction().get(getQuantityDragonsVisible());
            getDragonVisible(quantity).forEach(dragonModel -> dragonModel.setSelected(true));

            wait(() -> {
                this.removeDragons();

                handler.postDelayed(() -> {
                    players.changePlayer();
                    super.onClickButtonRemove(null);
                }, 400);
            });

        });
    }

    private void wait(Runnable runnable) {
        handler.postDelayed(runnable, 1000);
    }

    private void youWin() {
        showEndMessage(getString(R.string.parabens_voce_venceu));
        startWinnerMusic();
    }

    private void youLose() {
        showEndMessage(getString(R.string.voce_perdeu));
        startLoserMusic();
    }

}