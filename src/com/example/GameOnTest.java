package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameOnTest {
    String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";
    Layout siebelMap;

    @Before
    public void setUp() throws Exception {
        siebelMap = URLget.makeApiRequest(url);
    }

    @Test
    public void startingRoom() {
        assertEquals("MatthewsStreet", siebelMap.getStartingRoom());
    }

    @Test
    public void endingRoom() {
        assertEquals("Siebel1314", siebelMap.getEndingRoom());
    }

    @Test
    public void rooms() {
        assertEquals("SiebelEntry", siebelMap.getRooms()[1].getName());
    }

    @Test
    public void roomDescription() {
        assertEquals("You are in Siebel 1112.  There is space for two code reviews in this room.", siebelMap.getRooms()[4].getDescription());
    }

    @Test
    public void roomItems() {
        assertEquals(null, siebelMap.getRooms()[3].getItems());
    }

    @Test
    public void roomItems1() {
        assertEquals("bagel", siebelMap.getRooms()[5].getItems().get(0));
    }

    @Test
    public void roomDirections() {
        assertEquals("West", siebelMap.getRooms()[5].getDirections()[0].getDirectionName());
    }
}