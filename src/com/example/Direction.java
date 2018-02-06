package com.example;

public class Direction {
    private String directionName;
    private String room;

    public String getDirectionName() {
        return directionName;
    }

    public String getRoom() {
        return room;
    }

    public Room getRoomAsRoom(String room) {
        for (Room findMe: GameOn.currentLayout.getRooms()){
            if(findMe.getName().equalsIgnoreCase(getRoom())){
                return findMe;
            }
        }

    return null;
    }
}
