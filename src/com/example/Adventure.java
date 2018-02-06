/**
 * Starting with the startingRoom your game should display a message describing the player’s current
 environment. This should include five parts, with a newline separating them:
 1. The current room’s description printed verbatim
 2. If the room is the startingRoom print Your journey begins here
 3. If the room is the endingRoom print You have reached your final destination
 4. A list of the items in the current room.
 5. A list of the directions that the player can move.
 */

package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zilles on 9/19/17.
 */
public class Adventure {


    private static final int STATUS_OK = 200;

    public static void main(String[] arguments) {
        String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";


        // Make an HTTP request to the above URL
        try {
            makeApiRequest(url);
        } catch (UnirestException e) {
//            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }


    }

    public static Layout makeApiRequest(String url) throws UnirestException, MalformedURLException {
        Layout layout;
        final HttpResponse<String> stringHttpResponse;

        // This will throw MalformedURLException if the url is malformed.
        new URL(url);

        stringHttpResponse = Unirest.get(url).asString();
        // Check to see if the request was successful; if so, convert the payload JSON into Java objects
        if (stringHttpResponse.getStatus() == STATUS_OK) {
            String json = stringHttpResponse.getBody();
            Gson gson = new Gson();
            layout = gson.fromJson(json, Layout.class);
            return layout;
        } else return null;
    }

}