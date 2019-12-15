package com.spectron.dragoesdeshangrila.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;

import com.spectron.dragoesdeshangrila.R;
import com.spectron.dragoesdeshangrila.components.DialogEndGame;
import com.spectron.dragoesdeshangrila.enumerations.MusicEnum;
import com.spectron.dragoesdeshangrila.model.DragonModel;
import com.spectron.dragoesdeshangrila.model.PlayersModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

public abstract class PlayActivity extends AppCompatActivity {

    protected MediaPlayer mediaPlayer;
    protected Handler handler = new Handler();
    protected PlayersModel players;

    private List<DragonModel> dragons;
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);
        getWindow().setFlags(FLAG_LAYOUT_NO_LIMITS, FLAG_LAYOUT_NO_LIMITS);

        players = new PlayersModel(findViewById(R.id.player));

        chronometer = findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime() - 60);
        chronometer.start();
        chronometer.setOnChronometerTickListener(this::validateTime);
    }

    protected final void init(String firstPlayerName, String secondPlayerName) {
        players.setPlayers(firstPlayerName, secondPlayerName);

        startMusic(MusicEnum.GAME.getMusic());

        dragons = new ArrayList<>();
        for (int i = 1; i < 15; i++) {
            int id = getResources().getIdentifier("dragao" + i, "id", getPackageName());
            dragons.add(new DragonModel(findViewById(id)));
        }
    }

    private void validateTime(Chronometer chronometer) {
        String content = chronometer.getText().toString();
        int seconds = Integer.parseInt(content.split(":")[1]);

        if (seconds == 10) {
            players.changePlayer();
            chronometer.stop();
            onEndDragons();
        }
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

    public void onClickButtonRemove(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    protected abstract void onEndDragons();

    protected void showEndMessage(String message) {
        new DialogEndGame(this)
                .setMessage(message)
                .show();
    }

    protected boolean hasSelected() {
        return dragons.stream().anyMatch(DragonModel::isSelected);
    }

    private boolean canSelect() {
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
        startMusic(MusicEnum.LOSE.getMusic());
    }

    protected void startWinnerMusic() {
        startMusic(MusicEnum.WIN.getMusic());
    }

    private void startMusic(int music) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        mediaPlayer = MediaPlayer.create(this, music);
        mediaPlayer.start();
        mediaPlayer.setLooping(false);
    }

    protected List<DragonModel> getDragonVisible(int limit) {
        return dragons.stream().filter(DragonModel::isVisible).limit(limit).collect(Collectors.toList());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

}
