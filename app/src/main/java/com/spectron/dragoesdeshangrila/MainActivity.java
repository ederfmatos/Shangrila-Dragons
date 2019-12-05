package com.spectron.dragoesdeshangrila;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.spectron.dragoesdeshangrila.enumerations.LevelEnum;
import com.spectron.dragoesdeshangrila.model.DragonModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    private boolean isPlayerChance = true;

    private MediaPlayer mediaPlayer;

    private List<DragonModel> dragons;
    private Handler handler = new Handler();
    private LevelEnum level = LevelEnum.EASY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.fundojogo);
        dragons = new ArrayList<>();

        for (int i = 1; i < 15; i++) {
            int id = getResources().getIdentifier("dragao" + i, "id", getPackageName());
            dragons.add(new DragonModel(findViewById(id)));
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

    public void removeDragons(View view) {
        if (!hasSelected() || !isPlayerChance) return;

        removeDragons();

        if (getDragonsVisible() > 0) {
            isPlayerChance = false;
            vezdamaquina();
        } else {
            youWin();
        }

        dragons.forEach(dragon -> dragon.setSelected(false));
    }

    private void vezdamaquina() {
        int qdtRemove = level.getAction().get(getDragonsVisible());

        maquinaRemove(qdtRemove);

        if (getDragonsVisible() == 0) {
            isPlayerChance = true;

            handler.postDelayed(this::youLose, 200);
        }
    }

    private DragonModel getDragonVisible() {
        return dragons.stream().filter(DragonModel::isVisible).findFirst().orElse(null);
    }

    private void maquinaRemove(int quantity) {
        List<DragonModel> dragonsRemove = new ArrayList<>();

        while (quantity > 0) {
            DragonModel dragon = getDragonVisible();
            dragon.setSelected(true);
            dragonsRemove.add(dragon);
            quantity--;
        }

        handler.postDelayed(() -> {
            for (DragonModel dragon : dragonsRemove) {
                dragon.setInvisible();
            }

            isPlayerChance = true;
            Toast.makeText(this, "Sua vez", Toast.LENGTH_SHORT).show();
        }, 2000);
    }

    private boolean hasSelected() {
        return dragons.stream().anyMatch(DragonModel::isSelected);
    }

    private boolean canSelect() {
        return ((Long) dragons.stream().filter(dragonModel -> dragonModel.isSelected() && dragonModel.isVisible()).count()).intValue() < 3;
    }

    private int getDragonsVisible() {
        return ((Long) dragons.stream().filter(DragonModel::isVisible).count()).intValue();
    }

    public void removeDragons() {
        dragons.stream().filter(dragon -> dragon.isSelected() && dragon.isVisible()).forEach(dragon -> dragon.setInvisible().setSelected(false));
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

    private void youWin() {
    }

    private void youLose() {
        dragons.stream().forEach(DragonModel::setVisible);
    }
}