package com.example;

import java.util.Scanner;

public class userInput {
    public static String playerInput() {

        System.out.println("Type your command");
        Scanner input = new Scanner(System.in);
         String playerinput = input.nextLine();
        if(playerinput.equalsIgnoreCase("Exit") || playerinput.equalsIgnoreCase("QUIT")){
            System.out.print("Thanks for playing");
            System.exit(0);
        }
         char[] inputArray = playerinput.toCharArray();
         for(int i =0; i < inputArray.length; i++) {
             while(!())
             if(Character.isLetter(inputArray[i]) && Character.isWhitespace(inputArray[i+1]) && Character.isLetter(i+2) ){
                 return playerinput;
             }
             else {
                 System.out.print("Please input two words ");
             }
         }

        return null;
    }
}
