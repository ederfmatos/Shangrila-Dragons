package com.spectron.dragoesdeshangrila.database.repositories;

import com.spectron.dragoesdeshangrila.database.models.PlayerModel;

import java.util.Date;

public class PlayerRepository extends FirebaseRepository<PlayerModel> {

    public PlayerRepository() {
        super(PlayerModel.class);
    }

    @Override
    public void update(PlayerModel playerModel) {
        playerModel.setLoggedIn(new Date());
        super.update(playerModel);
    }

    @Override
    public void onChange(PlayerModel value) {

    }

    @Override
    public String getEntityName() {
        return "players";
    }

}
