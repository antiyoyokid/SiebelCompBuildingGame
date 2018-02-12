package com.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    private String name;
    private String description;
    private Direction[] directions;
    private ArrayList<Items> items;
    private ArrayList<Monster> monstersInRoom;

    public String getName() {
           return name;
    }

    public String getDescription() {
        return description;
    }

    public Direction[] getDirections() {
        return directions;
    }

    public ArrayList<Monster> getMonstersInRoom(){
        return monstersInRoom;
    }

    public ArrayList<Items> getItems() {
        return items;
    }
}
