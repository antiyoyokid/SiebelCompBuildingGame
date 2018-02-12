package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
//https://courses.engr.illinois.edu/cs126/adventure/siebel.json

public class GameOn {
    static Layout currentLayout;
    static URLget work = new URLget();


    private static ArrayList<Items> itemsCarried = new ArrayList<Items>();

    static {
        if (userInput.gameStart().equals("1")) {
            try {
                currentLayout = URLget.makeApiRequest(userInput.website());  // makes the Json files recieved be the layout
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            Gson fileReader = new Gson();
            currentLayout = fileReader.fromJson(work.getFileContentsAsString(userInput.localFile()), Layout.class);
        }

    }

    static Room currentRoom = findStartingRoom();

    /**
     * This is the main method, that prints you finished as long as currentRoom is not endingRoom
     */
    public static void main(String[] args) {


        while (currentRoom != findEndingRoom()) {
            printOutInfo(currentRoom);
            changeRoom(currentRoom);
        }
        System.out.print("You are at the ending room. You finished!");
    }

    /**
     * @return startingRoom from layout file
     */
    protected static Room findStartingRoom() {
        for (Room startingRoom : currentLayout.getRooms()) {
            if (startingRoom.getName().equalsIgnoreCase(currentLayout.getStartingRoom())) {
                return startingRoom;
            }
        }
        return null;
    }

    /**
     * @return endingRoom from layout file
     */
    protected static Room findEndingRoom() {
        for (Room endingRoom : currentLayout.getRooms()) {
            if (endingRoom.getName().equalsIgnoreCase(currentLayout.getEndingRoom())) {
                return endingRoom;
            }
        }
        return null;
    }

    /**
     * This method prints out all the current information for the current room for the User
     *
     * @param current current is the currentRoom
     */
    protected static void printOutInfo(Room current) {
        System.out.println(current.getDescription());


        if (current.getName().equalsIgnoreCase(currentLayout.getStartingRoom())) {
            System.out.println("Your journey begins here");
        }

        if (current.getName().equalsIgnoreCase((currentLayout.getEndingRoom()))) {
            System.out.println("You have reached your final destination");
        }

        if (current.getItems() != null) {
            for (Items items : current.getItems()) {
                System.out.println("You can take: " + items.getName());
            }
        }
        //if(current.getMonstersInRoom() == null) {
        for (Direction directions : current.getDirections()) {
            System.out.println("From here you can go: " + directions.getDirectionName());
        }

        if (current.getMonstersInRoom() != null) {
            for (Monster monster : current.getMonstersInRoom()) {
                System.out.println("Monsters in room: " + monster.getName());
            }
        }
    }


    protected static void duel(Monster a, Player player) {
        while (true) {
            String update = userInput.duelInput();
            if (update.equalsIgnoreCase("Disengage")){
                break;
            }
            else if (update.equalsIgnoreCase("attack")) {
                double damage = currentLayout.getPlayer()[0].getAttack() - a.getDefense();
                a.setHealth(a.getHealth() - damage);

                double monsterDamage = a.getAttack() - currentLayout.getPlayer()[0].getDefense();
                currentLayout.getPlayer()[0].setHealth(currentLayout.getPlayer()[0].getHealth() - monsterDamage);
                if (currentLayout.getPlayer()[0].getHealth() < 0) {
                    System.out.println("You died");
                    System.exit(0);
                }
                if (a.getHealth() < 0) {
                    System.out.println("You have defeated this monster");
                    currentRoom.getMonstersInRoom().remove(a);
                    break;
                }
            }
            else if (update.equalsIgnoreCase("status")) {
                System.out.println(currentLayout.getPlayer()[0].getHealth());
                System.out.println(a.getHealth());

            }

        }
    }

    protected static void duel(Monster a, Player player, Items item) {

        while (true) {
            String update = userInput.duelInput();
            if (update.equalsIgnoreCase("Disengage")){
                break;
            }
            else if (update.equalsIgnoreCase("attack")) {
                double damage = currentLayout.getPlayer()[0].getAttack() + item.getDamage() - a.getDefense();
                a.setHealth(a.getHealth() - damage);

                double monsterDamage = a.getAttack() - currentLayout.getPlayer()[0].getDefense();
                currentLayout.getPlayer()[0].setHealth(currentLayout.getPlayer()[0].getHealth() - monsterDamage);
                if (currentLayout.getPlayer()[0].getHealth() < 0) {
                    System.out.println("You died");
                    System.exit(0);
                }
                if (a.getHealth() < 0) {
                    System.out.println("You have defeated this monster");
                    currentRoom.getMonstersInRoom().remove(a);
                    break;
                }
            }
            else if (update.equalsIgnoreCase("status")) {
                System.out.println(currentLayout.getPlayer()[0].getHealth());
                System.out.println(a.getHealth());

            }


        }
    }

    /**
     * @param current current refers to the current room the player is in
     *                takes the current Room and uses userInput to manipulate current room
     */
    protected static void changeRoom(Room current) {

        String input = userInput.playerInput();
        String firstTerm = null;
        String secondTerm = null;

        /*
        If the input has two words,then, seperate the two words to check for take/drop items
         */
        if (input.contains(" ")) {
            firstTerm = input.split("\\s+")[0];
            secondTerm = input.split("\\s+")[1];
        }
        /*
        Code that chooses what Direction to Go
         */
        boolean ifDirectionDontExist = true;
        boolean ifItemDontExist = true;
        boolean ifItemCantBeDropped = true;
        boolean ifMonsterDontExist = true;
        /*
        For printing out list
        */
        if (input.equalsIgnoreCase("List")) {
            for (Items item : itemsCarried) {
                System.out.println("You are carrying " + item.getName());
            }
        } else if (input.equalsIgnoreCase("playerinfo")) {
            System.out.println("Here is the current player information: ");
            System.out.println("Name: " + currentLayout.getPlayer()[0].getName());
            System.out.println("Attack: " + currentLayout.getPlayer()[0].getAttack());
            System.out.println("Defense: " + currentLayout.getPlayer()[0].getDefense());
            System.out.println("Health: " + currentLayout.getPlayer()[0].getHealth());
            System.out.println("Level: " + currentLayout.getPlayer()[0].getLevel());
        }
        /*
        Makes sure input has to be two words except List above
         */
        else if (!input.contains(" ")) {
            System.out.println("Enter a valid input");
            currentRoom = current;
        }
        /*
        For going directions
         */
        else if (input.contains("duel".toLowerCase()) && current.getMonstersInRoom() != null) {

            for (int i = 0; i < current.getMonstersInRoom().size(); i++) {

                if (input.contains("duel " + current.getMonstersInRoom().get(i).getName().toLowerCase()) && (input.contains("with"))) {
                    System.out.println("You are now dueling with an item");
                    for (int j = 0; j < itemsCarried.size(); j++) {
                        if (input.contains(itemsCarried.get(i).getName())) {

                            duel(current.getMonstersInRoom().get(i), currentLayout.getPlayer()[0], itemsCarried.get(i));



                        }
                        currentRoom = current;
                        ifMonsterDontExist = false; //do later
                    }

                } else if (input.contains("duel " + current.getMonstersInRoom().get(i).getName().toLowerCase())) {
                    System.out.println("You are now in duel mode");

                    duel(current.getMonstersInRoom().get(i), currentLayout.getPlayer()[0]);
                }
                currentRoom = current;
                ifMonsterDontExist = false;
            }

            if (ifMonsterDontExist) {
                System.out.println("I can't duel " + secondTerm);
            }
        }


        else if (input.contains("go".toLowerCase())) {
            //if (currentRoom.getMonstersInRoom() == null) {
            for (Direction direction : current.getDirections()) {

                if (input.contains("go " + direction.getDirectionName().toLowerCase())) {
                    currentRoom = direction.getRoomAsRoom(direction.getRoom());
                    ifDirectionDontExist = false;
                }
            }
            if (ifDirectionDontExist) {
                System.out.println("You can't go  " + secondTerm + " direction");
            } else {
                System.out.println("There are still monsters in the room");
            }
        }


        /*
         For taking items
        */
        else if (firstTerm.contains("take") && current.getItems() != null) {
            for (int i = 0; i < current.getItems().size(); i++) {
                if (input.equalsIgnoreCase("take " + current.getItems().get(i).getName())) {
                    itemsCarried.add(current.getItems().get(i));
                    current.getItems().remove(i);
                    currentRoom = current;
                    ifItemDontExist = false;
                }
            }
            if (ifItemDontExist || current.getItems() == null) {
                System.out.println("This item doesn't exist");
            }
        }
        /*
        For Drop Item
        */
        else if (current.getItems() != null && itemsCarried != null && firstTerm.contains("drop")) {
            for (int i = 0; i < itemsCarried.size(); i++) {
                if (input.equalsIgnoreCase("drop " + itemsCarried.get(i).getName())) {
                    current.getItems().add(itemsCarried.get(i)); //need to make this itemsCarried of type Item
                    itemsCarried.remove(i);
                    currentRoom = current;
                    ifItemCantBeDropped = false;
                }
            }
            if (ifItemCantBeDropped) {
                System.out.println("Item can't be dropped");
            }
        }
        /*
        Catches bad inputs as they don't enter any if statements with an else statement at the end
         */

        else {
            System.out.println("Invalid input");
        }

    }
}