package com.spectron.dragoesdeshangrila.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.spectron.dragoesdeshangrila.R;

import p32929.officeaboutlib.Others.OfficeAboutHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showAboutPage(View view) {
        OfficeAboutHelper aboutPage = new OfficeAboutHelper(this, "https://raw.githubusercontent.com/ederfmatos/Shangrila-Dragons-Android/master/contributors.json");
        aboutPage.showAboutActivity();
    }

    public void showPlayActivity(View view) {
        new TTFancyGifDialog.Builder(MainActivity.this)
                .setTitle("Dragões de Shangrila")
                .setMessage("Selecione o tipo de partida")
                .setPositiveBtnText("Multiplayer")
                .setPositiveBtnBackground("#004d40")
                .setNegativeBtnText("Singleplayer")
                .setNegativeBtnBackground("#c1272d")
                .setGifResource(R.drawable.wallpaper)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(() -> showActivity(true))
                .OnNegativeClicked(() -> showActivity(false))
                .build();
    }

    private void showActivity(boolean isMultiplayer) {
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        intent.putExtra("isMultiplayer", isMultiplayer);
        startActivity(intent);
    }
}