package com.spectron.dragoesdeshangrila.database.models;

public class MovimentModel {

    private PlayerModel player;
    private Integer quantity;

    public MovimentModel() {

    }

    public MovimentModel(PlayerModel player, Integer quantity) {
        if (quantity == null || quantity < 1 || quantity > 3) {
            throw new RuntimeException("Jogada não permitida");
        }

        this.player = player;
        this.quantity = quantity;
    }

    public String getPlayer() {
        return player.getId();
    }

    public void setPlayer(String id) {
        if (player == null) {
            player = new PlayerModel(null, null, id);
        }
    }

    public Integer getQuantity() {
        return quantity;
    }

}
