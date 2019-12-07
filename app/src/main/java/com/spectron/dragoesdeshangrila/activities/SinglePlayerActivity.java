package com.spectron.dragoesdeshangrila.activities;

import android.os.Bundle;
import android.view.View;

import com.spectron.dragoesdeshangrila.R;
import com.spectron.dragoesdeshangrila.enumerations.LevelEnum;

import java.util.Objects;

public class SinglePlayerActivity extends PlayActivity {

    protected boolean isPlayerChance = true;
    private LevelEnum level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);

        super.init();

        level = (LevelEnum) Objects.requireNonNull(getIntent().getExtras()).get("level");
    }

    @Override
    public void onClickButtonRemove(View view) {
        if (!hasSelected() || !isPlayerChance) return;

        isPlayerChance = false;
        removeDragons();

        if (getQuantityDragonsVisible() > 0) {
            machineChance();
        }
    }

    @Override
    protected void onEndDragons() {
        if (isPlayerChance) {
            youLose();
        } else {
            youWin();
        }
    }

    private void machineChance() {
        handler.postDelayed(() -> machineRemove(level.getAction().get(getQuantityDragonsVisible())), 1000);
    }

    private void machineRemove(int quantity) {
        getDragonVisible(quantity).forEach(dragonModel -> dragonModel.setSelected(true));
        handler.postDelayed(this::removeDragons, 1000);
        isPlayerChance = true;
    }

    private void youWin() {
        showEndMessage("Você venceu", "Parabéns, você venceu");
        startWinnerMusic();
    }

    private void youLose() {
        showEndMessage("Puxa vida", "Que pena, você perdeu");
        startLoserMusic();
    }

}