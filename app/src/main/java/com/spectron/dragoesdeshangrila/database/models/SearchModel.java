package com.spectron.dragoesdeshangrila.database.models;

import java.util.Date;

public class SearchModel extends ShangrilaModel {

    private Date startSearch;
    private String possiblePlayer;

    SearchModel() {
        super();
        this.startSearch = new Date();
    }

    public SearchModel(PlayerModel player) {
        this();

        setId(player.getId());
    }

    public void setStartSearch(Date startSearch) {
        this.startSearch = startSearch;
    }

    public Date getStartSearch() {
        return startSearch;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("com.spectron.dragoesdeshangrila.database.models.SearchModel{");
        sb.append(", startSearch=").append(startSearch);
        sb.append(", id='").append(getId()).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getPossiblePlayer() {
        return possiblePlayer;
    }

    public void setPossiblePlayer(String possiblePlayer) {
        this.possiblePlayer = possiblePlayer;
    }
}