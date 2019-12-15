package com.spectron.dragoesdeshangrila.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.spectron.dragoesdeshangrila.R;
import com.spectron.dragoesdeshangrila.components.SpectronDialog;
import com.spectron.dragoesdeshangrila.enumerations.LevelEnum;
import com.spectron.dragoesdeshangrila.enumerations.MusicEnum;

import java.util.Arrays;
import java.util.List;

import p32929.officeaboutlib.Others.OfficeAboutHelper;

import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(FLAG_LAYOUT_NO_LIMITS, FLAG_LAYOUT_NO_LIMITS);

        mediaPlayer = MediaPlayer.create(this, MusicEnum.MENU.getMusic());
        mediaPlayer.start();
    }

    public void showAboutPage(View view) {
        OfficeAboutHelper aboutPage = new OfficeAboutHelper(this, "https://raw.githubusercontent.com/ederfmatos/Shangrila-Dragons-Android/master/contributors.json");
        aboutPage.showAboutActivity();
    }

    public void multiplayer(View view) {
        new SpectronDialog(this)
                .setMessage(getString(R.string.digite_o_nome_dos_jogadores))
                .setFirstTextHint(getString(R.string.jogador_1))
                .setSecondTextHint(getString(R.string.jogador_2))
                .setOnClickListener((players) -> showActivity(true, LevelEnum.HARD, players))
                .show();
    }

    public void singlePlayer(View view) {
        new SpectronDialog(this)
                .setMessage(getString(R.string.selecione_um_nivel))
                .setOptionsSelect(Arrays.asList(LevelEnum.values()))
                .onSelect((level) -> showActivity(false, level, null))
                .show();
    }

    private void showActivity(boolean isMultiplayer, LevelEnum level, List<String> players) {
        Class<? extends AppCompatActivity> clazz = SinglePlayerActivity.class;

        if(isMultiplayer) {
            clazz = MultiPlayerActivity.class;
        }

        Intent intent = new Intent(MainActivity.this, clazz);
        intent.putExtra("isMultiplayer", false);
        intent.putExtra("level", level);

        if(players != null) {
            intent.putExtra("players", players.toArray());
        }

        startActivity(intent);

        mediaPlayer.stop();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }
}