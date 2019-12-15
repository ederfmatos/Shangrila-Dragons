package com.spectron.dragoesdeshangrila.model;

import android.widget.TextView;

public class PlayersModel {

    private String firstPlayerName;
    private String secondPlayerName;

    private boolean isFirstPlayer;
    private TextView textView;

    public PlayersModel(TextView textView) {
        this.textView = textView;
    }

    public void setPlayers(String firstPlayerName, String secondPlayerName) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        changePlayer();
    }

    public String getFirstPlayer() {
        return firstPlayerName;
    }

    public String getSecondPlayer() {
        return secondPlayerName;
    }

    public String getCurrentPlayer() {
        return isFirstPlayer ? getFirstPlayer() : getSecondPlayer();
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public void changePlayer() {
        isFirstPlayer = !isFirstPlayer;
        textView.setText(getCurrentPlayer());
    }

}
