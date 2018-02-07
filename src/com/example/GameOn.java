package com.example;

        import com.mashape.unirest.http.exceptions.UnirestException;

        import java.net.MalformedURLException;
        import java.util.ArrayList;
//https://courses.engr.illinois.edu/cs126/adventure/siebel.json

public class GameOn {
    static final String url = userInput.website(); //sends request to site with Json File
    static Layout currentLayout;
    private static ArrayList<String> itemsCarried = new ArrayList<String>();

    static {
        try {
            currentLayout = URLget.makeApiRequest(url);  // makes the Json files recieved be the layout
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    static Room currentRoom = findStartingRoom();

    /**
     * This is the main method, that prints you finished as long as currentRoom is not endingRoom
     */
    public static void main(String[] args) {

        while (currentRoom != findEndingRoom()) {
            printOutInfo(currentRoom);
            roomAction(currentRoom);
        }
        System.out.print("You finished!");
    }

    /**
     * @return startingRoom from layout file
     */
    private static Room findStartingRoom() {
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
    private static Room findEndingRoom() {
        for (Room endingRoom : currentLayout.getRooms()) {
            if (endingRoom.getName().equalsIgnoreCase(currentLayout.getEndingRoom())) {
                return endingRoom;
            }
        }
        return null;
    }

    /**
     * This method prints out all the current information for the current room for the User
     * @param current current is the currentRoom
     */
    private static void printOutInfo (Room current) {
        System.out.println(current.getDescription());


        if (current.getName().equalsIgnoreCase(currentLayout.getStartingRoom())) {
            System.out.println("Your journey begins here");
        }

        if (current.getName().equalsIgnoreCase((currentLayout.getEndingRoom()))) {
            System.out.println("You have reached your final destination");
        }

        if (current.getItems() != null) {
            for (String items : current.getItems()) {
                System.out.println("You can take: " + items);
            }
        }

        for (Direction directions : current.getDirections()) {
            System.out.println("From here you can go: " + directions.getDirectionName());
        }


    }

    /**
     * @param current current refers to the current room the player is in
     * takes the current Room and uses userInput to manipulate current room
     */

    private static void roomAction (Room current) {

        String input = userInput.playerInput();
        String firstTerm = null;
        String secondTerm = null;



        /*
        If the input has two words,then I seperate the two words to check for take/drop items
         */
        if (input.contains(" " )) {
            firstTerm = input.split("\\s+")[0];
            secondTerm = input.split("\\s+")[1];
        }


        /*
        Code that chooses what Direction to Go
         */
        boolean ifDirectionExists = true;
        boolean ifItemExists  = true;
        boolean ifItemCanBeDropped = true;
        if(!input.contains(" ")) {
            System.out.println("Enter a valid input");
            currentRoom = current;
        }
        else if(input.contains("go".toLowerCase()) ) {
            for (Direction direction : current.getDirections()) {

                if (input.contains("go " + direction.getDirectionName().toLowerCase())) {
                    currentRoom = direction.getRoomAsRoom(direction.getRoom());
                    ifDirectionExists = false;
                }
            }
             if(ifDirectionExists) {
                System.out.println("You can't go  " + secondTerm + " direction");
            }
        } else if (firstTerm.contains("take")) {
            for (int i = 0; i < current.getItems().size(); i++) {
                if (input.equalsIgnoreCase("take " + current.getItems().get(i))) {
                    itemsCarried.add(current.getItems().get(i));
                    current.getItems().remove(i);
                    currentRoom = current;
                    ifItemExists = false;
                }
                if(ifItemExists) {
                    System.out.println ("");
                }
            }

        } else if (current.getItems() != null && itemsCarried != null && firstTerm.contains("drop")) {
            for (int i = 0; i < itemsCarried.size(); i++) {
                if (input.equalsIgnoreCase("drop " + itemsCarried.get(i))) {
                    current.getItems().add(itemsCarried.get(i));
                    itemsCarried.remove(i);
                    currentRoom = current;
                    ifItemCanBeDropped = false;
                }
            }
            if(ifItemCanBeDropped) {
                System.out.println("Item can't be picked");
            }
        }
        /*
        Catches bad inputs as they don't enter any if statements
         */
        else {
            System.out.println( "Invalid input");
        }
        System.out.println("You are carrying" + itemsCarried);

    }
}