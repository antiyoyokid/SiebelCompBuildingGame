package com.example;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.util.ArrayList;

import static com.sun.jmx.snmp.ThreadContext.contains;

public class Main {
    static final String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";
    static Layout currentLayout;
    private static ArrayList<String> itemsCarried = new ArrayList<String>();


    static {
        try {
            currentLayout = Adventure.makeApiRequest(url);
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    static Room currentRoom = findStartingRoom();

    public Main() throws MalformedURLException, UnirestException {
    }

    public static void main(String[] args) {
        while (currentRoom != findEndingRoom()) {
            roomStatus(currentRoom);
             }
      System.out.print("You finished!");
    }


    private static Room findStartingRoom() {
        for (Room startingRoom : currentLayout.getRooms()) {
            if (startingRoom.getName().equalsIgnoreCase(currentLayout.getStartingRoom())) {
                return startingRoom;
            }
        }
        return null;
    }

    private static Room findEndingRoom() {
        for (Room endingRoom : currentLayout.getRooms()) {
            if (endingRoom.getName().equalsIgnoreCase(currentLayout.getEndingRoom())) {
                return endingRoom;
            }
        }
        return null;
    }


    private static void roomStatus(Room current) {

        System.out.println(current.getDescription());


        if (current.getName().equalsIgnoreCase(currentLayout.getStartingRoom())) {

            System.out.println("Your journey begins here");

        }
        if (current.getName().equalsIgnoreCase((currentLayout.getEndingRoom()))) {
            System.out.println("You have reached your final destination");
        }

        if(current.getItems() != null ) {
            for (String items : current.getItems()) {

                System.out.println("You can take: " + items);
            }
        }


        for (Direction directions : current.getDirections()) {
            System.out.println("From here you can go: " + directions.getDirectionName());
        }

        String input = userInput.playerInput().toLowerCase();
        if(input.contains("east")) {

            for (Direction direction : current.getDirections()) {
                if ("East".contains(direction.getDirectionName())) {
                    currentRoom = direction.getRoomAsRoom(direction.getRoom());
                }
            }

        }
        if(input.contains("west")) {

            for (Direction direction : current.getDirections()) {
                if ("West".contains(direction.getDirectionName())) {
                    currentRoom = direction.getRoomAsRoom(direction.getRoom());
                }
            }

        }
        if(input.contains("south")) {

            for (Direction direction : current.getDirections()) {
                if ("South".contains(direction.getDirectionName())) {
                    currentRoom = direction.getRoomAsRoom(direction.getRoom());
                }
            }

        }
        if(input.contains("up")) {

            for (Direction direction : current.getDirections()) {
                if ("Up".contains(direction.getDirectionName())) {
                    currentRoom = direction.getRoomAsRoom(direction.getRoom());
                }
            }

        }
        if(input.contains("north")) {

            for (Direction direction : current.getDirections()) {
                if ("North".contains(direction.getDirectionName())) {
                    currentRoom = direction.getRoomAsRoom(direction.getRoom());
                }
            }

        }
        if(input.contains("northeast")) {

            for (Direction direction : current.getDirections()) {
                if ("NorthEast".contains(direction.getDirectionName())) {
                    currentRoom = direction.getRoomAsRoom(direction.getRoom());
                }
            }

        }
        if(input.contains("down")) {

            for (Direction direction : current.getDirections()) {
                if ("Down".contains(direction.getDirectionName())) {
                    currentRoom = direction.getRoomAsRoom(direction.getRoom());
                }
            }

        }


        if(current.getItems()!= null && current.getItems().contains(input)) {

            for (int i =0; i < current.getItems().size(); i++) {
                if(input.contains(current.getItems().get(i))){
                    itemsCarried.add(current.getItems().get(i));
                    current.getItems().remove(i);
                    currentRoom = current;
                }
            }

        }
        System.out.println(itemsCarried);

    }
}









