package com.example.paweek.jjump.database;

public class Result {

    private String player;
    private Integer points;

    public Result(String player, Integer points) {
        this.player = player;
        this.points = points;
    }

    public String getPlayer() {
        return player;
    }

    public Integer getPoints() {
        return points;
    }
}
