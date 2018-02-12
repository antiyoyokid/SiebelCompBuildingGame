package com.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    private String name;
    private String description;
    private Direction[] directions;
    private ArrayList<String> items;
    private Monster[] monstersInRoom;

    public String getName() {
           return name;
    }

    public String getDescription() {
        return description;
    }

    public Direction[] getDirections() {
        return directions;
    }

    public ArrayList<String> getItems() {
        return items;
    }
    public Monster[] getMonstersInRoom(){
        return monstersInRoom;
    }

}
