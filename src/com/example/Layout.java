package com.example;

public class Layout {
    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;
    private Monster[] monsters;
    private Player[] Player;


    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public Monster[] getMonsters() {
        return monsters;
    }

    public Player[] getPlayer() {
        return Player;
    }
}
