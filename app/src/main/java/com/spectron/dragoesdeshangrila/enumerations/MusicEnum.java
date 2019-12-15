package com.spectron.dragoesdeshangrila.enumerations;

import com.spectron.dragoesdeshangrila.R;

public enum MusicEnum {

    MENU(R.raw.fundojogo),
    GAME(R.raw.fundojogo),
    WIN(R.raw.winner),
    LOSE(R.raw.lose);

    private int music;

    MusicEnum(int music) {
        this.music = music;
    }

    public int getMusic() {
        return music;
    }

}
