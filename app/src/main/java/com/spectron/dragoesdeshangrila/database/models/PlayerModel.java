package com.spectron.dragoesdeshangrila.database.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerModel extends ShangrilaModel {

    private String name;
    private String email;
    private List<MatchModel> matches;
    private Date loggedIn;

    public PlayerModel() {

    }

    public PlayerModel(String email, String name, String id) {
        this.name = name;
        this.email = email;
        this.setId(id);
        matches = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<MatchModel> getMatcheDates() {
        return matches;
    }

    public Date getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Date loggedIn) {
        this.loggedIn = loggedIn;
    }
}
