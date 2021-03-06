package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
//https://courses.engr.illinois.edu/cs126/adventure/siebel.json

public class GameOn {
    static Layout currentLayout;
    static URLget work = new URLget ( );
    static double Experience = 0;
    private static ArrayList<Items> itemsCarried = new ArrayList<Items> ( );

    static {
        if (userInput.gameStart ( ).equals ("1")) {
            try {
                currentLayout = URLget.makeApiRequest (userInput.website ( )); // makes the Json files recieved be the layout
            } catch (UnirestException e) {
                e.printStackTrace ( );
            } catch (MalformedURLException e) {
                e.printStackTrace ( );
            }
        } else {
            Gson fileReader = new Gson ( );
            currentLayout = fileReader.fromJson (work.getFileContentsAsString (userInput.localFile ( )), Layout.class);
        }

    }

    static Player aishik = currentLayout.getPlayer ( )[0];
    static Room currentRoom = findStartingRoom ( );
    static double initialAttack = aishik.getAttack ( );
    static double initialDefense = aishik.getDefense ( );
    static double initialHealth = aishik.getHealth ( );


    /**
     * @return startingRoom from layout file
     */
    protected static Room findStartingRoom() {
        for (Room startingRoom : currentLayout.getRooms ( )) {
            if (startingRoom.getName ( ).equalsIgnoreCase (currentLayout.getStartingRoom ( ))) {
                return startingRoom;
            }
        }
        return null;
    }

    /**
     * @return endingRoom from layout file
     */
    protected static Room findEndingRoom() {
        for (Room endingRoom : currentLayout.getRooms ( )) {
            if (endingRoom.getName ( ).equalsIgnoreCase (currentLayout.getEndingRoom ( ))) {
                return endingRoom;
            }
        }
        return null;
    }

    /**
     * This is the main method, that prints you finished as long as currentRoom is not endingRoom
     */
    public static void main(String[] args) {

        while (currentRoom != findEndingRoom ( )) {
            printOutInfo (currentRoom);
            changeRoom (currentRoom);
        }
        System.out.print ("You are at the ending room. You finished!");
    }


    /**
     * This method prints out all the current information for the current room for the User
     *
     * @param current current is the currentRoom
     */
    protected static void printOutInfo(Room current) {
        System.out.println (current.getDescription ( ));


        if (current.getName ( ).equalsIgnoreCase (currentLayout.getStartingRoom ( ))) {
            System.out.println ("Your journey begins here");
        }

        if (current.getName ( ).equalsIgnoreCase ((currentLayout.getEndingRoom ( )))) {
            System.out.println ("You have reached your final destination");
        }

        if (current.getItems ( ) != null) {
            for (Items items : current.getItems ( )) {
                System.out.println ("You can take: " + items.getName ( ));
            }
        }
        if (current.getMonstersInRoom ( ) == null || current.getMonstersInRoom ( ).size ( ) == 0) {
            for (Direction directions : current.getDirections ( )) {
                System.out.println ("From here you can go: " + directions.getDirectionName ( ));
            }
        }

        if (current.getMonstersInRoom ( ) != null) {
            for (Monster monster : current.getMonstersInRoom ( )) {
                System.out.println ("Monsters in room: " + monster.getName ( ));
            }
        }
    }


    /**
     * @param current current refers to the current room the player is in
     *                takes the current Room and uses userInput to manipulate current room
     */
    protected static void changeRoom(Room current) {

        String input = userInput.playerInput ( );
        String firstTerm = null;
        String secondTerm = null;

  /*
  If the input has two words,then, seperate the two words to check for take/drop items
   */
        if (input.contains (" ")) {
            firstTerm = input.split ("\\s+")[0];
            secondTerm = input.split ("\\s+")[1];
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
        if (input.equalsIgnoreCase ("List")) {
            for (Items item : itemsCarried) {
                System.out.println ("You are carrying " + item.getName ( ));
            }
        } else if (input.equalsIgnoreCase ("playerinfo")) {
            System.out.println ("Here is the current player information: ");
            System.out.println ("Name: " + aishik.getName ( ));
            System.out.println ("Attack: " + aishik.getAttack ( ));
            System.out.println ("Defense: " + aishik.getDefense ( ));
            System.out.println ("Health: " + aishik.getHealth ( ));
            System.out.println ("Level: " + aishik.getLevel ( ));
        }
  /*
  Makes sure input has to be two words except List above
   */
        else if (!input.contains (" ")) {
            System.out.println ("Enter a valid input");
            currentRoom = current;
        }
  /*
  For going directions
   */
        else if (input.contains ("duel".toLowerCase ( )) && current.getMonstersInRoom ( ) != null) {

            for (int i = 0; i < current.getMonstersInRoom ( ).size ( ); i++) {

                if (input.contains ("duel " + current.getMonstersInRoom ( ).get (i).getName ( ).toLowerCase ( )) && (input.contains ("with"))) {
                    System.out.println ("You are now dueling with an item");
                    for (int j = 0; j < itemsCarried.size ( ); j++) {
                        if (input.contains (itemsCarried.get (i).getName ( )) && current.getMonstersInRoom ( ).size ( ) != 0) {

                            duel (current.getMonstersInRoom ( ).get (i), aishik, itemsCarried.get (i));

                        }
                        currentRoom = current;
                        ifMonsterDontExist = false; //do later
                    }

                } else if (input.contains ("duel " + current.getMonstersInRoom ( ).get (i).getName ( ).toLowerCase ( ))) {
                    System.out.println ("You are now in duel mode");

                    duel (current.getMonstersInRoom ( ).get (i), aishik);
                }
                currentRoom = current;
                ifMonsterDontExist = false;
            }

            if (ifMonsterDontExist) {
                System.out.println ("I can't duel " + secondTerm);
            }
        } else if (input.contains ("go".toLowerCase ( ))) {
            if (currentRoom.getMonstersInRoom ( ) == null || currentRoom.getMonstersInRoom ( ).size ( ) == 0) {
                for (Direction direction : current.getDirections ( )) {

                    if (input.contains ("go " + direction.getDirectionName ( ).toLowerCase ( ))) {
                        currentRoom = direction.getRoomAsRoom (direction.getRoom ( ));
                        ifDirectionDontExist = false;
                    }
                }
                if (ifDirectionDontExist) {
                    System.out.println ("You can't go  " + secondTerm + " direction");
                }
            } else {
                System.out.println ("There are still monsters in the room");
            }
        }


  /*
   For taking items
  */
        else if (firstTerm.contains ("take") && current.getItems ( ) != null) {
            for (int i = 0; i < current.getItems ( ).size ( ); i++) {
                if (input.equalsIgnoreCase ("take " + current.getItems ( ).get (i).getName ( ))) {
                    itemsCarried.add (current.getItems ( ).get (i));
                    current.getItems ( ).remove (i);
                    currentRoom = current;
                    ifItemDontExist = false;
                }
            }
            if (ifItemDontExist || current.getItems ( ) == null) {
                System.out.println ("This item doesn't exist");
            }
        }
  /*
  For Drop Item
  */
        else if (current.getItems ( ) != null && itemsCarried != null && firstTerm.contains ("drop")) {
            for (int i = 0; i < itemsCarried.size ( ); i++) {
                if (input.equalsIgnoreCase ("drop " + itemsCarried.get (i).getName ( ))) {
                    current.getItems ( ).add (itemsCarried.get (i)); //need to make this itemsCarried of type Item
                    itemsCarried.remove (i);
                    currentRoom = current;
                    ifItemCantBeDropped = false;
                }
            }
            if (ifItemCantBeDropped) {
                System.out.println ("Item can't be dropped");
            }
        }
  /*
  Catches bad inputs as they don't enter any if statements with an else statement at the end
   */
        else {
            System.out.println ("Invalid input");
        }

    }

    protected static void duel(Monster a, Player player) {
        double oldPlayerHealth = aishik.getHealth ( );
        double oldMonsterHealth = a.getHealth ( );
        while (true) {
            String update = userInput.duelInput ( );
            if (update.equalsIgnoreCase ("Disengage")) {
                break;
            } else if (update.equalsIgnoreCase ("attack")) {
                double damage = aishik.getAttack ( ) - a.getDefense ( );
                a.setHealth (a.getHealth ( ) - damage);

                double monsterDamage = a.getAttack ( ) - aishik.getDefense ( );
                aishik.setHealth (aishik.getHealth ( ) - monsterDamage);
                if (aishik.getHealth ( ) < 0) {
                    System.out.println ("You died");
                    System.exit (0);
                }
                if (a.getHealth ( ) <= 0) {
                    System.out.println ("You have defeated this monster");

                    Experience += Math.abs ((((a.getAttack ( ) + a.getDefense ( )) / 2) + a.getHealth ( )) * 20);
                    if (Experience > experienceRequirement (aishik.getLevel ( ))) {
                        aishik.setLevel (aishik.getLevel ( ) + 1);
                        aishik.setHealth (initialHealth * Math.pow (1.3, aishik.getLevel ( )));
                        aishik.setAttack (initialAttack * Math.pow (1.5, aishik.getLevel ( )));
                        aishik.setDefense (initialDefense * Math.pow (1.5, aishik.getLevel ( )));
                    }
                    currentRoom.getMonstersInRoom ( ).remove (a);
                    break;
                }
            } else if (update.equalsIgnoreCase ("status")) {
                double playerRatio = aishik.getHealth ( ) / oldPlayerHealth;

                if (playerRatio < 0.2) {
                    System.out.println ("Player Status : " + "#____");
                } else if (playerRatio < 0.4) {
                    System.out.println ("Player Status : " + "##___");
                } else if (playerRatio < 0.6) {
                    System.out.println ("Player Status : " + "###__");
                } else if (playerRatio < 0.8) {
                    System.out.println ("Player Status : " + "####_");
                } else if (playerRatio <= 1) {
                    System.out.println ("Player Status : " + "#####");
                }

                double monstaRatio = a.getHealth ( ) / oldMonsterHealth;
                if (monstaRatio < 0.2) {
                    System.out.println ("Monster Status : " + "#____");
                } else if (monstaRatio < 0.4) {
                    System.out.println ("Monster Status : " + "##___");
                } else if (monstaRatio < 0.6) {
                    System.out.println ("Monster Status : " + "###__");
                } else if (monstaRatio < 0.8) {
                    System.out.println ("Monster Status : " + "####_");
                } else if (monstaRatio == 1) {
                    System.out.println ("Monster Status : " + "#####");
                }


            }


        }
    }

    protected static double experienceRequirement(int playerLevel) {

        if (playerLevel == 1) {
            return 25;
        }
        if (playerLevel == 2) {
            return 50;
        }
        return ((experienceRequirement (-1) + experienceRequirement (-2)) * 1.1);
    }

    protected static void duel(Monster a, Player player, Items item) {
        double oldPlayerHealth = aishik.getHealth ( );
        double oldMonsterHealth = a.getHealth ( );
        while (true) {
            String update = userInput.duelInput ( );
            if (update.equalsIgnoreCase ("Disengage")) {
                break;
            } else if (update.equalsIgnoreCase ("attack")) {
                double damage = aishik.getAttack ( ) + item.getDamage ( ) - a.getDefense ( );
                a.setHealth (a.getHealth ( ) - damage);

                double monsterDamage = a.getAttack ( ) - aishik.getDefense ( );
                aishik.setHealth (aishik.getHealth ( ) - monsterDamage);
                if (aishik.getHealth ( ) < 0) {
                    System.out.println ("You died");
                    System.exit (0);
                }
                if (a.getHealth ( ) < 0) {
                    System.out.println ("You have defeated this monster");
                    currentRoom.getMonstersInRoom ( ).remove (a);
                    Experience += ((a.getAttack ( ) + a.getDefense ( )) / 2 + a.getHealth ( )) * 20;
                    if (Experience >= experienceRequirement (aishik.getLevel ( ))) {
                        aishik.setLevel (aishik.getLevel ( ) + 1);
                        aishik.setHealth (initialHealth * Math.pow (1.3, aishik.getLevel ( )));
                        aishik.setAttack (initialAttack * Math.pow (1.3, aishik.getLevel ( )));
                        aishik.setDefense (initialDefense * Math.pow (1.3, aishik.getLevel ( )));
                    }

                    break;
                }
            } else if (update.equalsIgnoreCase ("status")) {
                System.out.println ("You have defeated this monster");

                Experience += Math.abs ((((a.getAttack ( ) + a.getDefense ( )) / 2) + a.getHealth ( )) * 20);
                if (Experience > experienceRequirement (aishik.getLevel ( ))) {
                    aishik.setLevel (aishik.getLevel ( ) + 1);
                    aishik.setHealth (initialHealth * Math.pow (1.3, aishik.getLevel ( )));
                    aishik.setAttack (initialAttack * Math.pow (1.3, aishik.getLevel ( )));
                    aishik.setDefense (initialDefense * Math.pow (1.3, aishik.getLevel ( )));
                }
                currentRoom.getMonstersInRoom ( ).remove (a);
                break;
            } else if (update.equalsIgnoreCase ("status")) {
                double playerRatio = aishik.getHealth ( ) / oldPlayerHealth;

                if (playerRatio < 0.2) {
                    System.out.println ("Player Status : " + "#____");
                } else if (playerRatio < 0.4) {
                    System.out.println ("Player Status : " + "##___");
                } else if (playerRatio < 0.6) {
                    System.out.println ("Player Status : " + "###__");
                } else if (playerRatio < 0.8) {
                    System.out.println ("Player Status : " + "####_");
                } else if (playerRatio <= 1) {
                    System.out.println ("Player Status : " + "#####");
                }

                double monstaRatio = a.getHealth ( ) / oldMonsterHealth;
                if (monstaRatio < 0.2) {
                    System.out.println ("Monster Status : " + "#____");
                } else if (monstaRatio < 0.4) {
                    System.out.println ("Monster Status : " + "##___");
                } else if (monstaRatio < 0.6) {
                    System.out.println ("Monster Status : " + "###__");
                } else if (monstaRatio < 0.8) {
                    System.out.println ("Monster Status : " + "####_");
                } else if (monstaRatio == 1) {
                    System.out.println ("Monster Status : " + "#####");
                }


            }


        }
    }
}