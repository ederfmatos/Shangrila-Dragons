package com.spectron.dragoesdeshangrila.activities;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.spectron.dragoesdeshangrila.R;
import com.spectron.dragoesdeshangrila.model.DragonModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class PlayActivity extends AppCompatActivity {

    protected MediaPlayer mediaPlayer;
    private boolean isRunning;
    protected TextView player;

    protected List<DragonModel> dragons;
    protected Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);

        player = findViewById(R.id.player);
    }

    protected final void init() {
        if (isRunning) {
            return;
        }

        startMusic(R.raw.fundojogo);
        dragons = new ArrayList<>();

        for (int i = 1; i < 15; i++) {
            int id = getResources().getIdentifier("dragao" + i, "id", getPackageName());
            dragons.add(new DragonModel(findViewById(id)));
        }
        isRunning = true;
    }

    public void onDragonClick(View view) {
        Optional<DragonModel> optional = dragons.stream().filter(dragon -> dragon.getId().equals(view.getId())).findFirst();

        if (optional.isPresent()) {
            DragonModel dragon = optional.get();

            if (dragon.isSelected()) {
                dragon.setSelected(false);
            } else if (canSelect()) {
                dragon.toggleSelection();
            }
        }
    }

    public abstract void onClickButtonRemove(View view);

    protected abstract void onEndDragons();

    protected void showEndMessage(String title, String message) {
//        new TTFancyGifDialog.Builder(SinglePlayerActivity.this)
//                .setTitle(title)
//                .setMessage(message)
//                .setPositiveBtnText("Sair")
//                .setPositiveBtnBackground("#004d40")
//                .setNegativeBtnText("Reiniciar jogo")
//                .setNegativeBtnBackground("#c1272d")
//                .setGifResource(R.drawable.wallpaper)
//                .isCancellable(true)
//                .OnPositiveClicked(this::finish)
//                .OnNegativeClicked(this::recreate)
//                .build();
    }

    protected boolean hasSelected() {
        return dragons.stream().anyMatch(DragonModel::isSelected);
    }

    protected boolean canSelect() {
        return ((Long) dragons.stream().filter(dragonModel -> dragonModel.isSelected() && dragonModel.isVisible()).count()).intValue() < 3;
    }

    protected Integer getQuantityDragonsVisible() {
        return ((Long) dragons.stream().filter(DragonModel::isVisible).count()).intValue();
    }

    protected void removeDragons() {
        dragons.stream().filter(dragon -> dragon.isSelected() && dragon.isVisible()).forEach(dragon -> dragon.setInvisible().setSelected(false));

        if (getQuantityDragonsVisible() == 0) {
            onEndDragons();
        }
    }

    protected void startLoserMusic() {
        startMusic(R.raw.lose);
    }

    protected void startWinnerMusic() {
        startMusic(R.raw.winner);
    }

    private void startMusic(int music) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        mediaPlayer = MediaPlayer.create(this, music);
        mediaPlayer.start();
    }

    protected List<DragonModel> getDragonVisible(int limit) {
        return dragons.stream().filter(DragonModel::isVisible).limit(limit).collect(Collectors.toList());
    }

    protected final void setPlayerName(String playerName) {
        player.setText(playerName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            if (!mediaPlayer.isLooping()) {
                mediaPlayer.setLooping(true);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
