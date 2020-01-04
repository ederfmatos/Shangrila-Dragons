package com.spectron.dragoesdeshangrila.database.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MatchModel extends ShangrilaModel {

    private PlayerModel firstPlayer;
    private PlayerModel secondPlayer;
    private final Date startIn;
    private Date endIn;
    private List<MovimentModel> moviments;
    private boolean isFinished;
    private String winner;
    private int dragons = 15;

    private MatchModel() {
        super();
        this.startIn = new Date();
    }

    public MatchModel(PlayerModel firstPlayer, PlayerModel secondPlayer) {
        this();
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.moviments = new ArrayList<>();
        this.isFinished = false;
    }

    public void finish() {
        this.isFinished = true;
        winner = moviments.get(moviments.size() - 1).getPlayer();
        endIn = new Date();
    }

    public PlayerModel getFirstPlayer() {
        return firstPlayer;
    }

    public PlayerModel getSecondPlayer() {
        return secondPlayer;
    }

    public Date getStartIn() {
        return startIn;
    }

    public Date getEndIn() {
        return endIn;
    }

    public List<MovimentModel> getMoviments() {
        return moviments;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public String getWinner() {
        return winner;
    }

    public void addMoviment(MovimentModel moviment) {
        dragons -= moviment.getQuantity();
        this.moviments.add(moviment);

        if (dragons == 0) {
            this.finish();
        }
    }

    public int getDragons() {
        return dragons;
    }
}
