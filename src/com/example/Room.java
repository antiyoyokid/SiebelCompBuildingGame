package com.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    private String name;
    private String description;
    private Direction[] directions;
    private ArrayList<String> items;

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

}
