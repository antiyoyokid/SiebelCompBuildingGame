package com.example;

public class Main {


    //public void whereAmI(Room current) {
    public static void main(String[] args) {
         Room currentRoom = Adventure.layout.getRooms()[0];

        if(currentRoom.getName().equalsIgnoreCase(Adventure.layout.getStartingRoom())) {
            System.out.println(currentRoom.getDescription());
            System.out.println("Your journey begins here");

        }
        if(currentRoom.getName().equalsIgnoreCase((Adventure.layout.getEndingRoom()))) {
            System.out.println(currentRoom.getDescription());
            System.out.println("You have reached your final destination");
        }

        for(String items: currentRoom.getItems()) {
            System.out.print(items);
        }
        for(Direction directions: currentRoom.getDirections()){
            System.out.print(directions);
        }


    }
}
