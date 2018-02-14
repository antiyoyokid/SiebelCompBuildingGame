/**
 * Starting with the startingRoom your game should display a message describing the player’s current
 * environment. This should include five parts, with a newline separating them:
 * 1. The current room’s description printed verbatim
 * 2. If the room is the startingRoom print Your journey begins here
 * 3. If the room is the endingRoom print You have reached your final destination
 * 4. A list of the items in the current room.
 * 5. A list of the directions that the player can move.
 */

package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Gets URL
 * Given by professor on Github
 */
public class URLget {
    private static final int STATUS_OK = 200;

    /**
     * @param url String URL that the userInputs
     * @return layout for the game
     * @throws UnirestException
     * @throws MalformedURLException
     */
    public static Layout makeApiRequest(String url) throws UnirestException, MalformedURLException {
        Layout layout;
        final HttpResponse<String> stringHttpResponse;

        // This will throw MalformedURLException if the url is malformed.
        new java.net.URL (url);

        stringHttpResponse = Unirest.get (url).asString ( );
        // Check to see if the request was successful; if so, convert the payload JSON into Java objects
        if (stringHttpResponse.getStatus ( ) == STATUS_OK) {
            String json = stringHttpResponse.getBody ( );
            Gson gson = new Gson ( );
            layout = gson.fromJson (json, Layout.class);
            return layout;
        } else return null;
    }

    public String getFileContentsAsString(String filename) {

        // Java uses Paths as an operating system-independent specification of the location of files.
        // In this case, we're looking for files that are in a directory called 'data' located in the
        // root directory of the project, which is the 'current working directory'.
        final Path path = FileSystems.getDefault ( ).getPath ("data", filename);

        try {
            // Read all of the bytes out of the file specified by 'path' and then convert those bytes
            // into a Java String.  Because this operation can fail if the file doesn't exist, we
            // include this in a try/catch block
            return new String (Files.readAllBytes (path));
        } catch (IOException e) {
            // Since we couldn't find the file, there is no point in trying to continue.  Let the
            // user know what happened and exit the run of the program.  Note: we're only exiting
            // in this way because we haven't talked about exceptions and throwing them in CS 126 yet.
            System.out.println ("Couldn't find file: " + filename);
            System.exit (-1);
            return null;  // note that this return will never execute, but Java wants it there.
        }
    }

}
