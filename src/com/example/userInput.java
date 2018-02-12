package com.example;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.util.Scanner;


public class userInput {
       public static String playerInput() {

        System.out.println("Type your command");

        Scanner input = new Scanner(System.in);

        String playerinput = input.nextLine();
        /*
        The line below gets rid of trailing spaces and multiple spaces in between words
         */
        playerinput = playerinput.toLowerCase().trim().replaceAll(" +", " ");

        if (playerinput.equalsIgnoreCase("Exit") || playerinput.equalsIgnoreCase("QUIT")) {
            System.out.print("Thanks for playing");
            System.exit(0);
        }
        return playerinput;
    }

    public static String website() {
        System.out.println("Enter a URL");
        Scanner input = new Scanner(System.in);
        String url = input.nextLine();

        return url;
    }

    public static String localFile() {
        System.out.println("Enter file name");
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine() + ".json";

        return fileName;
    }

    public static String gameStart() {
        System.out.println("Welcome summoner");
        System.out.println("To load map from URL press 1, else to load from local file 2");
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine();

       return choice;
    }

    public static String duelInput(){
           System.out.println("Fight");
           Scanner input = new Scanner(System.in);
           String duelInput = input.nextLine().trim().toLowerCase();

           return duelInput;
    }
}

